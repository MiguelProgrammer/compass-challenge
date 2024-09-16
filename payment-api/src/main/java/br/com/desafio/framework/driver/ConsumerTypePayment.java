package br.com.desafio.framework.driver;

import br.com.desafio.adapter.in.PaymentItem;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerTypePayment {

    @SqsListener("PARTIAL")
    public PaymentItem listenerPartial(PaymentItem paymentItem) {
        log.info("\nListener Partial topic ...{}", paymentItem);
        return paymentItem;
    }

    @SqsListener("TOTAL")
    public PaymentItem listenerTotal(PaymentItem paymentItem) {
        log.info("\nListener Total topic ...{}", paymentItem);
        return paymentItem;
    }

    @SqsListener("SURPLUS")
    public PaymentItem listenerSurplus(PaymentItem paymentItem) {
        log.info("\nListener Surplus topic ...{}", paymentItem);
        return paymentItem;
    }
}
