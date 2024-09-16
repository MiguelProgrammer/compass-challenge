package br.com.desafio.usecase.payment.partial;

import br.com.desafio.adapter.in.Payment;
import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.config.exception.PaymentException;
import br.com.desafio.config.exception.SellerException;
import br.com.desafio.core.domain.PaymentModel;
import br.com.desafio.core.domain.enums.PaymentType;
import br.com.desafio.core.usecase.payment.notification.Notification;
import br.com.desafio.core.usecase.payment.partial.PartialUseCase;
import br.com.desafio.core.usecase.payment.surplus.SurplusUseCase;
import br.com.desafio.core.usecase.payment.total.TotalUseCase;
import br.com.desafio.infra.gateways.PaymentRepositoryGateway;
import br.com.desafio.infra.gateways.drivers.ConsumerGateway;
import br.com.desafio.infra.gateways.drivers.PublisherGateway;
import br.com.desafio.infra.persitence.h2.entities.PaymentEntity;
import br.com.desafio.infra.persitence.h2.repositories.PaymentItemRepository;
import br.com.desafio.infra.persitence.h2.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PartialUseCaseTest {

    @InjectMocks
    private PublisherGateway publisher;

    @InjectMocks
    private ConsumerGateway subscriber;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentItemRepository paymentItemRepository;

    @InjectMocks
    private PaymentRepositoryGateway paymentRepositoryGateway;

    @InjectMocks
    private PartialUseCase partialUseCase;

    @InjectMocks
    private TotalUseCase totalUseCase;

    @InjectMocks
    private SurplusUseCase surplusUseCase;

    @Mock
    private Payment payment;

    @Mock
    private List<PaymentItem> paymentItems;

    @Mock
    private PaymentItem paymentItem;

    @BeforeEach
    void setUp() {
        this.payment = new Payment();
        this.paymentItems = new ArrayList<>();
        this.paymentItem = new PaymentItem();

        payment.setSellerId(1L);
        paymentItem.setPaymentStatus(PaymentType.PARTIAL);
        paymentItem.setPaymentValue(new BigDecimal("10.00"));
        paymentItems.add(paymentItem);
        payment.setPaymentItems(paymentItems);

        paymentRepositoryGateway = new PaymentRepositoryGateway(paymentRepository, paymentItemRepository);

        partialUseCase = new PartialUseCase(
                publisher,subscriber,paymentRepositoryGateway,
                totalUseCase, surplusUseCase);
    }

    @Test
    @DisplayName("Process payments")
    void confirmPaymentTypes() {

         /**
         * ARANGE
         */
        Mockito.when(paymentRepository.findBySellerId(payment.getSellerId()).getSellerId()).thenReturn(1L);

        /**
         * ACT
         */
        PaymentModel confirm = partialUseCase.confirm(payment.paymentRequestToDomain());

        /**
         * ASSERT
         */
        Assertions.assertFalse(ObjectUtils.isEmpty(confirm.getPaymentItemModels().get(0).getItemId()));
        Assertions.assertNotEquals(confirm.getPaymentItemModels().get(0).getPaymentStatus(), paymentItems.get(0).getPaymentStatus());
        Assertions.assertEquals(confirm.getPaymentItemModels().get(0).getPaymentStatus(), paymentItems.get(0).getPaymentStatus());
        Assertions.assertThrows(SellerException.class, () -> paymentRepositoryGateway.getPaymentId(999L), Notification.SELLER_NOT_FOUND);
        Assertions.assertThrows(PaymentException.class, () -> paymentRepositoryGateway.getSeller(999L), Notification.PAYMENT_ID_NOT_FOUND);
    }

}
