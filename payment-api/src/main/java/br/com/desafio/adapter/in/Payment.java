package br.com.desafio.adapter.in;

import br.com.desafio.core.domain.PaymentItemModel;
import br.com.desafio.core.domain.PaymentModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @JsonProperty("seller_id")
    private Long sellerId;

    @JsonProperty("payment_items")
    private List<PaymentItem> paymentItems;

    public PaymentModel paymentRequestToDomain() {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setSellerId(sellerId);
        paymentModel.setPaymentItemModels(mappedItensPaymentToRequest(this.paymentItems));
        return paymentModel;
    }

    public Payment paymentDomainToRequest(PaymentModel paymentDomainResponse) {
        return Payment.builder()
                .sellerId(paymentDomainResponse.getSellerId())
                .paymentItems(mappedItensPaymentToResponse(paymentDomainResponse)).build();
    }

    private List<PaymentItem> mappedItensPaymentToResponse(PaymentModel paymentDomainResponse) {
        List<PaymentItem> paymentItems = new ArrayList<>();
        paymentDomainResponse.getPaymentItemModels().forEach(item -> {
            PaymentItem paymentItem = PaymentItem.builder()
                    .itemId(item.getItemId())
                    .paymentId(item.getPaymentId())
                    .paymentValue(item.getPaymentValue())
                    .paymentStatus(item.getPaymentStatus()).build();
            paymentItems.add(paymentItem);
        });
        return paymentItems;
    }

    private List<PaymentItemModel> mappedItensPaymentToRequest(List<PaymentItem> paymentItems) {
        List<PaymentItemModel> itensPayment = new ArrayList<>();
        paymentItems.forEach(item -> {
            PaymentItemModel paymentItemModel = new PaymentItemModel();
            validateIdItem(item, paymentItemModel);
            paymentItemModel.setItemId(item.getItemId());
            paymentItemModel.setPaymentId(item.getPaymentId());
            paymentItemModel.setPaymentValue(item.getPaymentValue());
            paymentItemModel.setPaymentStatus(item.getPaymentStatus());
            itensPayment.add(paymentItemModel);
        });
        return itensPayment;
    }

    private void validateIdItem(PaymentItem item, PaymentItemModel paymentItemModel) {
        paymentItemModel.setItemId(item.getItemId() == null ? item.getPaymentId() : item.getItemId());
    }
}
