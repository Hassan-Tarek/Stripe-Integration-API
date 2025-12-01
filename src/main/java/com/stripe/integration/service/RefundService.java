package com.stripe.integration.service;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.RefundRequest;
import com.stripe.integration.dto.response.RefundResponse;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import org.springframework.stereotype.Service;

@Service
public class RefundService {

    public RefundResponse refund(RefundRequest request) throws StripeException {
        RefundCreateParams.Builder paramsBuilder = RefundCreateParams.builder()
                .setPaymentIntent(request.getPaymentIntentId());

        if (request.getAmount() != null) {
            paramsBuilder.setAmount(request.getAmount());
        }

        if (request.getReason() != null) {
            paramsBuilder.setReason(RefundCreateParams.Reason.valueOf(request.getReason()));
        }

        Refund refund = Refund.create(paramsBuilder.build());
        return new RefundResponse(
                refund.getId(),
                refund.getAmount(),
                refund.getCurrency(),
                refund.getStatus()
        );
    }
}
