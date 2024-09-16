package br.com.desafio.infra.gateways.drivers;

import br.com.desafio.adapter.controllers.ConsumerController;
import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.framework.driver.helper.DriverHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerGateway {

    @Autowired
    private DriverHelper driverHelper;

    @Autowired
    private ConsumerController consumerController;

    public PaymentItemModel consumer(PaymentItemModel paymentItemModels) {
        PaymentItem paymentItem = driverHelper.mappedItensPaymentToResponse(paymentItemModels);
        return mappedItemRequestToDomain(consumerController.selectTopic(paymentItem));
    }

    private PaymentItemModel mappedItemRequestToDomain(PaymentItem paymentItem) {
        PaymentItemModel paymentItemModel = new PaymentItemModel();
        paymentItemModel.setItemId(paymentItem.getItemId());
        paymentItemModel.setPaymentId(paymentItem.getPaymentId());
        paymentItemModel.setPaymentValue(paymentItem.getPaymentValue());
        paymentItemModel.setPaymentStatus(paymentItem.getPaymentStatus());
        return paymentItemModel;
    }
}
