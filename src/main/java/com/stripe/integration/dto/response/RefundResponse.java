package com.stripe.integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {
    private String id;
    private Long amount;
    private String currency;
    private String status;
}
