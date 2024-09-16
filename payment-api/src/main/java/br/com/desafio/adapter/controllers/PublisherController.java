package br.com.desafio.adapter.controllers;

import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.framework.driver.PublisherTypePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherController {

    @Autowired
    private PublisherTypePayment publisherTypePayment;

    public void publisher(PaymentItem paymentItem) {
        publisherTypePayment.publisherPartial(paymentItem);
    }
}
