package br.com.desafio.core.domain;

import java.util.List;

public class PaymentModel {

    private Long sellerId;
    private List<PaymentItemModel> paymentItemModels;

    public PaymentModel() {
    }

    public PaymentModel(Long sellerId, List<PaymentItemModel> paymentItemModels) {
        this.sellerId = sellerId;
        this.paymentItemModels = paymentItemModels;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public List<PaymentItemModel> getPaymentItemModels() {
        return paymentItemModels;
    }

    public void setPaymentItemModels(List<PaymentItemModel> paymentItemModels) {
        this.paymentItemModels = paymentItemModels;
    }
}
