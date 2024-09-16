package br.com.desafio.adapter.controllers;

import br.com.desafio.adapter.in.Payment;
import br.com.desafio.core.domain.PaymentModel;
import br.com.desafio.core.usecase.ConfirmPaymentUseCase;
import br.com.desafio.core.usecase.payment.partial.PartialUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentController {

    @Autowired
    private ConfirmPaymentUseCase confirmPaymentUseCase;

    @Autowired
    private PartialUseCase partialUseCase;

    public Payment payment(Payment request) {

        log.info("Making payment ...");
        PaymentModel paymentDomainResponse = partialUseCase.confirm(request.paymentRequestToDomain());

        log.info("Mapping return to return response ...");
        return request.paymentDomainToRequest(paymentDomainResponse);
    }

}
