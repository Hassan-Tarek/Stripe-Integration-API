package com.stripe.integration.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class CheckoutSessionRequest {

    @NotNull(message = "Items list cannot be null")
    private List<LineItem> items;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Success URL is required")
    private String successUrl;

    @NotBlank(message = "Cancel URL is required")
    private String cancelUrl;

    @Data
    public static class LineItem {

        @NotBlank(message = "Product name is required")
        private String productName;

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        private Long amount; // in cents

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        private Long quantity = 1L;
    }
}
