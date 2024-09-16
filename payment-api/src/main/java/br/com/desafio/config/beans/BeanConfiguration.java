package br.com.desafio.config.beans;

import br.com.desafio.adapter.controllers.ConsumerController;
import br.com.desafio.core.usecase.ConfirmPaymentUseCase;
import br.com.desafio.core.usecase.payment.partial.PartialUseCase;
import br.com.desafio.core.usecase.payment.surplus.SurplusUseCase;
import br.com.desafio.core.usecase.payment.total.TotalUseCase;
import br.com.desafio.framework.driver.ConsumerTypePayment;
import br.com.desafio.infra.gateways.PaymentRepositoryGateway;
import br.com.desafio.infra.gateways.drivers.ConsumerGateway;
import br.com.desafio.infra.gateways.drivers.PublisherGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfiguration {

    @Bean
    @Primary
    public ConfirmPaymentUseCase confirmPaymentUseCase(
            PublisherGateway publisher, ConsumerGateway subscriber,
            PaymentRepositoryGateway paymentRepositoryGateway, TotalUseCase totalUseCase,
            SurplusUseCase surplusUseCase) {
        return new PartialUseCase(publisher, subscriber, paymentRepositoryGateway, totalUseCase, surplusUseCase);
    }

    @Bean
    public ConsumerController consumerController(ConsumerTypePayment consumerTypePayment) {
        return new ConsumerController(consumerTypePayment);
    }


}
