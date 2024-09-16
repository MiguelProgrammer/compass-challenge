package br.com.desafio.core.usecase.payment.surplus;

import br.com.desafio.adapter.gateway.PaymentGateway;
import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.core.domain.PaymentModel;
import br.com.desafio.core.domain.enums.PaymentType;
import br.com.desafio.core.usecase.ConfirmPaymentUseCase;
import br.com.desafio.infra.gateways.PaymentRepositoryGateway;
import br.com.desafio.infra.gateways.drivers.ConsumerGateway;
import br.com.desafio.infra.gateways.drivers.PublisherGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurplusUseCase implements ConfirmPaymentUseCase {

    private PublisherGateway publisher;
    private ConsumerGateway subscriber;
    private final PaymentGateway paymentGateway;
    private final PaymentRepositoryGateway paymentRepositoryGateway;

    public SurplusUseCase(PublisherGateway publisher, ConsumerGateway subscriber, PaymentGateway paymentGateway, PaymentRepositoryGateway paymentRepositoryGateway) {
        this.publisher = publisher;
        this.subscriber = subscriber;
        this.paymentGateway = paymentGateway;
        this.paymentRepositoryGateway = paymentRepositoryGateway;
    }

    @Override
    public PaymentModel confirm(PaymentModel paymentModel) {
        paymentModel.setPaymentItemModels(paymentSurplusValidation(paymentModel));
        return paymentModel;
    }

    /**
     * INTERNAL VALIDATION RULES PAYMENT VALUES
     */
    private List<PaymentItemModel> paymentSurplusValidation(PaymentModel paymentModel) {
        return paymentModel.getPaymentItemModels().stream().map(pay -> {
            PaymentItemModel listPaymentIdsDB = paymentRepositoryGateway.getPaymentId(pay.getPaymentId());
            return paymentValidationPaymentToSend(pay, listPaymentIdsDB);
        }).collect(Collectors.toList());
    }

    /**
     * VALIDATE VALUES PAYMENTE
     */
    private PaymentItemModel paymentValidationPaymentToSend(PaymentItemModel pmRequest, PaymentItemModel pmItemDB) {
        PaymentItemModel paymentItemModel = new PaymentItemModel();
        paymentItemModel.setPaymentId(pmRequest.getPaymentId());
        paymentItemModel.setPaymentValue(pmRequest.getPaymentValue());
        paymentItemModel.setPaymentStatus(pmRequest.getPaymentStatus());
        paymentItemModel = partialPrepared(pmRequest, pmItemDB, paymentItemModel);
        return paymentItemModel;
    }

    private PaymentItemModel partialPrepared(PaymentItemModel pmRequest, PaymentItemModel pmItemDB, PaymentItemModel paymentItemModel) {

        if (pmRequest.getPaymentValue().compareTo(pmItemDB.getPaymentValue()) > 0) {
            pmRequest.setItemId(pmItemDB.getItemId());
            pmRequest.setPaymentStatus(PaymentType.SURPLUS);
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
        if (paymentIdDB.getPaymentValue().compareTo(paymentId.getPaymentValue()) >= 0) {
            paymentItemModel.setPaymentValue(paymentIdDB.getPaymentValue().subtract(paymentId.getPaymentValue()));
        }
        paymentRepositoryGateway.savePayment(paymentItemModel);
    }
}

