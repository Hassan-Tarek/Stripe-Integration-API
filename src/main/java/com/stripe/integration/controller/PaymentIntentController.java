package com.stripe.integration.controller;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.PaymentIntentRequest;
import com.stripe.integration.dto.response.PaymentIntentResponse;
import com.stripe.integration.service.PaymentIntentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-intent")
@CrossOrigin(origins = "*")
public class PaymentIntentController {

    private final PaymentIntentService paymentIntentService;

    @Autowired
    public PaymentIntentController(final PaymentIntentService paymentIntentService) {
        this.paymentIntentService = paymentIntentService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
            @Valid @RequestBody PaymentIntentRequest request) {
        try {
            PaymentIntentResponse response = paymentIntentService.createPaymentIntent(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentIntentResponse> getPaymentIntent(
            @PathVariable String id) {
        try {
            PaymentIntentResponse response = paymentIntentService.getPaymentIntent(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<PaymentIntentResponse> confirmPaymentIntent(
            @PathVariable String id) {
        try {
            PaymentIntentResponse response = paymentIntentService.confirmPaymentIntent(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PaymentIntentResponse> cancelPaymentIntent(
            @PathVariable String id) {
        try {
            PaymentIntentResponse response = paymentIntentService.cancelPaymentIntent(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
