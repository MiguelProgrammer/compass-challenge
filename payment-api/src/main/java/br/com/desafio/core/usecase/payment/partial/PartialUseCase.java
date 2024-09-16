package br.com.desafio.core.usecase.payment.partial;

import br.com.desafio.config.exception.PaymentException;
import br.com.desafio.config.exception.SellerException;
import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.core.domain.PaymentModel;
import br.com.desafio.core.domain.enums.PaymentType;
import br.com.desafio.core.usecase.ConfirmPaymentUseCase;
import br.com.desafio.core.usecase.payment.surplus.SurplusUseCase;
import br.com.desafio.core.usecase.payment.total.TotalUseCase;
import br.com.desafio.infra.gateways.PaymentRepositoryGateway;
import br.com.desafio.infra.gateways.drivers.ConsumerGateway;
import br.com.desafio.infra.gateways.drivers.PublisherGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.desafio.core.usecase.payment.notification.Notification.PAYMENT_ID_NOT_FOUND;
import static br.com.desafio.core.usecase.payment.notification.Notification.SELLER_NOT_FOUND;

@Component
public class PartialUseCase implements ConfirmPaymentUseCase {

    private final PublisherGateway publisher;
    private final ConsumerGateway subscriber;
    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final TotalUseCase totalUseCase;
    private final SurplusUseCase surplusUseCase;

    public PartialUseCase(PublisherGateway publisher, ConsumerGateway subscriber, PaymentRepositoryGateway paymentRepositoryGateway, TotalUseCase totalUseCase, SurplusUseCase surplusUseCase) {
        this.publisher = publisher;
        this.subscriber = subscriber;
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.totalUseCase = totalUseCase;
        this.surplusUseCase = surplusUseCase;
    }

    public PaymentModel confirm(PaymentModel paymentModel) {
        sellerValidation(paymentModel);
        paymentValidation(paymentModel);
        paymentModel.setPaymentItemModels(paymentPartialValidation(paymentModel));
        return paymentModel;
    }


    /**
     * INTERNAL VALIDATION PAYMENT ID EXISTS
     */
    private void paymentValidation(PaymentModel paymentModel) {
        paymentModel.getPaymentItemModels().forEach(pay -> {
            if (paymentRepositoryGateway.getPaymentId(pay.getPaymentId()).getPaymentId() == null) {
                throw new PaymentException(PAYMENT_ID_NOT_FOUND);
            }
        });
    }

    /**
     * INTERNAL VALIDATION SELLER EXISTS
     */
    private void sellerValidation(PaymentModel paymentModel) {
        if (paymentRepositoryGateway.getSeller(paymentModel.getSellerId()) == null) {
            throw new SellerException(SELLER_NOT_FOUND);
        }
    }

    private PaymentItemModel paymentValidationPaymentToSend(PaymentItemModel pmRequest, PaymentItemModel pmItemDB) {
        PaymentItemModel paymentItemModel = new PaymentItemModel();
        paymentItemModel.setPaymentId(pmRequest.getPaymentId());
        paymentItemModel.setPaymentValue(pmRequest.getPaymentValue());
        paymentItemModel.setPaymentStatus(pmRequest.getPaymentStatus());
        paymentItemModel = partialPrepared(pmRequest, pmItemDB, paymentItemModel);
        return paymentItemModel;
    }

    private PaymentItemModel partialPrepared(PaymentItemModel pmRequest, PaymentItemModel pmItemDB, PaymentItemModel paymentItemModel) {

        if (pmRequest.getPaymentValue().compareTo(pmItemDB.getPaymentValue()) < 0) {
            pmRequest.setItemId(pmItemDB.getItemId());
            pmRequest.setPaymentStatus(PaymentType.PARTIAL);
            paymentItemModel = processPayment(pmRequest);
            paymentItemModel.setPaymentStatus(pmRequest.getPaymentStatus());
            paymentItemModel.setPaymentValue(paymentItemModel.getPaymentValue());
        }

        return paymentItemModel;
    }

    private PaymentItemModel processPayment(PaymentItemModel pmRequest) {
        publisher.send(pmRequest);
        PaymentItemModel paymentIdDB = paymentRepositoryGateway.getPaymentId(pmRequest.getItemId());
        PaymentItemModel paymentId = subscriber.consumer(pmRequest);
        updatePayment(pmRequest, paymentIdDB, paymentId, pmRequest);
        return pmRequest;
    }

    private void updatePayment(PaymentItemModel pmRequest, PaymentItemModel paymentIdDB, PaymentItemModel paymentId, PaymentItemModel paymentItemModel) {
        paymentItemModel.setPaymentStatus(pmRequest.getPaymentStatus());
        if(paymentIdDB.getPaymentValue().compareTo(paymentId.getPaymentValue()) >= 0) {
            paymentItemModel.setPaymentValue(paymentIdDB.getPaymentValue().subtract(paymentId.getPaymentValue()));
        }
        paymentRepositoryGateway.savePayment(paymentItemModel);
    }

    private List<PaymentItemModel> paymentPartialValidation(PaymentModel paymentModel) {

        List<PaymentItemModel> paymentItemResponse = paymentModel.getPaymentItemModels().stream().map(pay -> {
            PaymentItemModel listPaymentIdsDB = paymentRepositoryGateway.getPaymentId(pay.getPaymentId());
            return paymentValidationPaymentToSend(pay, listPaymentIdsDB);
        }).collect(Collectors.toList());

        if(paymentItemResponse.get(0).getItemId() == null){
            paymentItemResponse = totalUseCase.confirm(paymentModel).getPaymentItemModels();
        }

        if(paymentItemResponse.get(0).getItemId() == null){
            paymentItemResponse = surplusUseCase.confirm(paymentModel).getPaymentItemModels();
        }

        return paymentItemResponse;
    }
}