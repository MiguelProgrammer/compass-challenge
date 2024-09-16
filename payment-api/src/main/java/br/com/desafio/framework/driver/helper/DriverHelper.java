package br.com.desafio.framework.driver.helper;

import br.com.desafio.adapter.in.PaymentItem;
import br.com.desafio.core.domain.PaymentItemModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DriverHelper {

    @Value("${localstack.cloud.queue.partial}")
    private String topicPartial;

    @Value("${localstack.cloud.queue.total}")
    private String topicTotal;

    @Value("${localstack.cloud.queue.surplus}")
    private String topicSurplus;


    public String selectTopic(String typeQueue) {
        return switch (typeQueue) {
            case "PARTIAL" -> topicPartial;
            case "TOTAL" -> topicTotal;
            case "SURPLUS" -> topicSurplus;
            default -> "type topic not found";
        };
    }

    public PaymentItem mappedItensPaymentToResponse(PaymentItemModel paymentItemModel) {
        return PaymentItem.builder()
                .itemId(paymentItemModel.getItemId())
                .paymentId(paymentItemModel.getPaymentId())
                .paymentValue(paymentItemModel.getPaymentValue())
                .paymentStatus(paymentItemModel.getPaymentStatus()).build();
    }

}
