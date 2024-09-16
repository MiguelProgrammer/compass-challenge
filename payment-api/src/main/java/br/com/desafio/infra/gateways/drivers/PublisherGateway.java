package br.com.desafio.infra.gateways.drivers;

import br.com.desafio.adapter.controllers.PublisherController;
import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.framework.driver.helper.DriverHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherGateway {

    @Autowired
    private DriverHelper driverHelper;

    @Autowired
    private PublisherController publisherController;

    public void send(PaymentItemModel paymentItemModel) {
        PaymentItem paymentItem = driverHelper.mappedItensPaymentToResponse(paymentItemModel);
        publisherController.publisher(paymentItem);
    }
}
