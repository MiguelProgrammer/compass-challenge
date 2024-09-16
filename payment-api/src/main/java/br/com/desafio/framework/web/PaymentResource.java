package br.com.desafio.framework.web;


import br.com.desafio.adapter.controllers.PaymentController;
import br.com.desafio.adapter.in.Payment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentResource {

    @Autowired
    private PaymentController paymentController;

    @PutMapping(path = "/api/payment")
    public ResponseEntity<Payment> setPayment(@RequestBody Payment request) {
        Payment payment = paymentController.payment(request);
        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }
}
