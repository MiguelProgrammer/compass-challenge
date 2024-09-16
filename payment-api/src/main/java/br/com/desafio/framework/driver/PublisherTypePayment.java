package br.com.desafio.framework.driver;

import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.framework.driver.helper.DriverHelper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherTypePayment {

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private DriverHelper driverHelper;

    public void publisherPartial(PaymentItem paymentItem) {
        log.info("Publisher in topic -> {}", paymentItem.getPaymentStatus());
        sqsTemplate.send(driverHelper.selectTopic(String.valueOf(paymentItem.getPaymentStatus())), paymentItem);
    }


}
