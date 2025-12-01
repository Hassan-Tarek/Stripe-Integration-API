package com.stripe.integration.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscriptionRequest {

    @NotBlank(message = "Customer id is required")
    private String customerId;

    @NotBlank(message = "Price id is required")
    private String priceId;

    private String paymentMethodId;

    private Long trialDays;
}
