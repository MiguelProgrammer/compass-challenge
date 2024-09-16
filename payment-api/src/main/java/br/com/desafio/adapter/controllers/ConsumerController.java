package br.com.desafio.adapter.controllers;

import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.framework.driver.ConsumerTypePayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerController {

    private final ConsumerTypePayment consumerTypePayment;

    public ConsumerController(ConsumerTypePayment consumerTypePayment) {
        this.consumerTypePayment = consumerTypePayment;
    }

    public PaymentItem selectTopic(PaymentItem paymentItem) {
        String messageStatusNotFound = "Type status not found";
        return (PaymentItem) switch (paymentItem.getPaymentStatus().toString()) {
            case "PARTIAL" -> (PaymentItem) consumerPartial(paymentItem);
            case "TOTAL" -> (PaymentItem) consumerTotal(paymentItem);
            case "SURPLUS" -> (PaymentItem) consumerSurplus(paymentItem);
            default -> messageStatusNotFound;
        };
    }

    private PaymentItem consumerPartial(PaymentItem paymentItem) {
        return consumerTypePayment.listenerPartial(paymentItem);
    }

    private PaymentItem consumerTotal(PaymentItem paymentItem) {
        return consumerTypePayment.listenerTotal(paymentItem);
    }

    private PaymentItem consumerSurplus(PaymentItem paymentItem) {
        return consumerTypePayment.listenerSurplus(paymentItem);
    }
}
