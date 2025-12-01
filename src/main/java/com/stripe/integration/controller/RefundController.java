package com.stripe.integration.controller;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.RefundRequest;
import com.stripe.integration.dto.response.RefundResponse;
import com.stripe.integration.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/refund")
@CrossOrigin(origins = "*")
public class RefundController {

    private final RefundService refundService;

    @Autowired
    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping("/refund")
    public ResponseEntity<RefundResponse> refund(
            @Valid @RequestBody RefundRequest request) {
        try {
            RefundResponse response = refundService.refund(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
