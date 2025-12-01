package com.stripe.integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutSessionResponse {
    private String id;
    private String url;
    private Long totalAmount;
    private String currency;
    private String paymentStatus;
    private Long expiresAt;
}
