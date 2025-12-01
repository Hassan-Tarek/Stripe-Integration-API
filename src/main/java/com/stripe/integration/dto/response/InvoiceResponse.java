package com.stripe.integration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private String id;
    private Long amountDue;
    private String currency;
    private String status;
    private String hostedInvoiceUrl;
    private String invoicePdf;
}
