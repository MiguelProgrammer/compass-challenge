package br.com.desafio.core.usecase;


import br.com.desafio.core.domain.PaymentModel;

public interface ConfirmPaymentUseCase {

    PaymentModel confirm(PaymentModel paymentModel);
}
