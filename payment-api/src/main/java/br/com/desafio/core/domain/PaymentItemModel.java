package br.com.desafio.core.domain;

import br.com.desafio.core.domain.enums.PaymentType;

import java.math.BigDecimal;

public class PaymentItemModel {

    private Long itemId;
    private Long paymentId;
    private BigDecimal paymentValue;
    private PaymentType paymentStatus;

    public PaymentItemModel() {
    }

    public PaymentItemModel(Long itemId, Long paymentId, BigDecimal paymentValue, PaymentType paymentStatus) {
        this.itemId = itemId;
        this.paymentId = paymentId;
        this.paymentValue = paymentValue;
        this.paymentStatus = paymentStatus;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(BigDecimal paymentValue) {
        this.paymentValue = paymentValue;
    }

    public PaymentType getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
