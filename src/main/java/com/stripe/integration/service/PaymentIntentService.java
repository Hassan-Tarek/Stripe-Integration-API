package com.stripe.integration.service;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.PaymentIntentRequest;
import com.stripe.integration.dto.response.PaymentIntentResponse;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentIntentService {

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) throws StripeException {
        PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                );

        if (request.getDescription() != null) {
            paramsBuilder.setDescription(request.getDescription());
        }

        if (request.getCustomerId() != null) {
            paramsBuilder.setCustomer(request.getCustomerId());
        }

        if (request.getMetadata() != null && !request.getMetadata().isEmpty()) {
            paramsBuilder.putAllMetadata(request.getMetadata());
        }

        PaymentIntent paymentIntent = PaymentIntent.create(paramsBuilder.build());
        return mapToResponse(paymentIntent);
    }

    public PaymentIntentResponse getPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return mapToResponse(paymentIntent);
    }

    public PaymentIntentResponse confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                .build();
        paymentIntent = paymentIntent.confirm(confirmParams);
        return mapToResponse(paymentIntent);
    }

    public PaymentIntentResponse cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntentCancelParams cancelParams = PaymentIntentCancelParams.builder()
                .build();
        paymentIntent = paymentIntent.cancel(cancelParams);
        return mapToResponse(paymentIntent);
    }

    private PaymentIntentResponse mapToResponse(PaymentIntent paymentIntent) {
        return new PaymentIntentResponse(
                paymentIntent.getId(),
                paymentIntent.getAmount(),
                paymentIntent.getCurrency(),
                paymentIntent.getStatus(),
                paymentIntent.getClientSecret()
        );
    }
}
