package com.stripe.integration.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RefundRequest {

    @NotBlank(message = "Payment intent id is required")
    private String paymentIntentId;

    @Positive(message = "Amount must be greater than zero")
    private Long amount; // for partial refund

    private String reason;
}
