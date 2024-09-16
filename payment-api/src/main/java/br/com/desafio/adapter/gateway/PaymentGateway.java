package br.com.desafio.adapter.gateway;

import br.com.desafio.core.domain.PaymentItemModel;

public interface PaymentGateway {
    Long getSeller(Long sellerId);

    PaymentItemModel getPaymentId(Long paymentId);

    void savePayment(PaymentItemModel paymentItemModel);
}
