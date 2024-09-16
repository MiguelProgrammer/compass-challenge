package br.com.desafio.adapter.in;

import br.com.desafio.core.domain.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentItem {

    @JsonProperty("item_id")
    private Long itemId;

    @NotBlank(message = "The payment id attribute cannot be null or empty")
    @JsonProperty("payment_id")
    private Long paymentId;
    
    @DecimalMin(value = "0.10")
    @JsonProperty("payment_value")
    private BigDecimal paymentValue;

    @NotBlank(message = "The status payment attribute cannot be null or empty")
    @JsonProperty("payment_status")
    private PaymentType paymentStatus;

}
