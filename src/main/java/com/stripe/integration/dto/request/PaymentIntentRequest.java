package com.stripe.integration.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Map;

@Data
public class PaymentIntentRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Long amount; // in cents

    @NotBlank(message = "Currency is required")
    private String currency;

    private String description;

    private String customerId;

    private Map<String, String> metadata;
}
