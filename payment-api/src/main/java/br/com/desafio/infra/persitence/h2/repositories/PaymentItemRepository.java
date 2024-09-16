package br.com.desafio.infra.persitence.h2.repositories;

import br.com.desafio.infra.persitence.h2.entities.PaymentItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentItemRepository extends JpaRepository<PaymentItemEntity, Long> {

    @Query("select p from PaymentItemEntity p where p.itemId =:id")
    PaymentItemEntity gettemIdById(@Param("id") Long id);

}
