package com.stripe.integration.controller;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.CheckoutSessionRequest;
import com.stripe.integration.dto.response.CheckoutSessionResponse;
import com.stripe.integration.service.CheckoutSessionService;
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
@RequestMapping("/api/checkout-session")
@CrossOrigin(origins = "*")
public class CheckoutSessionController {

    private final CheckoutSessionService checkoutSessionService;

    @Autowired
    public CheckoutSessionController(final CheckoutSessionService checkoutSessionService) {
        this.checkoutSessionService = checkoutSessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<CheckoutSessionResponse> createCheckoutSession(
            @Valid @RequestBody CheckoutSessionRequest request) {
        try {
            CheckoutSessionResponse response = checkoutSessionService.createCheckoutSession(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutSessionResponse> getCheckoutSession(
            @PathVariable String id) {
        try {
            CheckoutSessionResponse response = checkoutSessionService.getCheckoutSession(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
