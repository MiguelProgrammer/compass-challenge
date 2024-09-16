package br.com.desafio.infra.persitence.h2.entities;

import br.com.desafio.core.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_payment")
@SequenceGenerator(name = "item_sequence")
public class PaymentItemEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "value")
    private BigDecimal paymentValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentType paymentStatus;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private PaymentEntity sellerPayment;
}
