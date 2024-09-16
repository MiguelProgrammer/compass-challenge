package br.com.desafio.infra.persitence.h2.repositories;

import br.com.desafio.infra.persitence.h2.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    PaymentEntity findBySellerId(Long sellerId);
}
