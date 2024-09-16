package br.com.desafio.config.exception;

import br.com.desafio.core.usecase.payment.notification.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = Notification.SELLER_NOT_FOUND)
public class SellerException extends RuntimeException {

    public SellerException(String message) {
        super(message);
    }
}
