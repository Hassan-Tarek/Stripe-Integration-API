package com.stripe.integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private String id;
    private String status;
    private Long startDate;
    private Long endDate;
    private String latestInvoiceId;
}
