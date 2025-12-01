package com.stripe.integration.service;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.InvoiceRequest;
import com.stripe.integration.dto.response.InvoiceResponse;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    public InvoiceResponse createInvoice(InvoiceRequest request) throws StripeException {
        InvoiceItemCreateParams itemParams = InvoiceItemCreateParams.builder()
                .setCustomer(request.getCustomerId())
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setDescription(request.getDescription() == null ? "charge" : request.getDescription())
                .build();

        InvoiceItem.create(itemParams);

        InvoiceCreateParams.Builder invoiceParamsBuilder = InvoiceCreateParams.builder()
                .setCustomer(request.getCustomerId())
                .setAutoAdvance(true);      // Stripe will automatically attempt to pay if default payment method exists

        if (request.getMetadata() != null && !request.getMetadata().isEmpty()) {
            invoiceParamsBuilder.putAllMetadata(request.getMetadata());
        }

        Invoice invoice = Invoice.create(invoiceParamsBuilder.build());
        Invoice finalized = invoice.finalizeInvoice();
        return mapToInvoiceResponse(finalized);
    }

    public InvoiceResponse getInvoice(String invoiceId) throws StripeException {
        Invoice invoice = Invoice.retrieve(invoiceId);
        return mapToInvoiceResponse(invoice);
    }

    private InvoiceResponse mapToInvoiceResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getAmountDue(),
                invoice.getCurrency(),
                invoice.getStatus(),
                invoice.getHostedInvoiceUrl(),
                invoice.getInvoicePdf()
        );
    }
}
