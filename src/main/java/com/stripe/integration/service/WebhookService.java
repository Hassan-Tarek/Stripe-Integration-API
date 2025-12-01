package com.stripe.integration.service;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebhookService {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public Event constructEvent(String payload, String sigHeader) throws SignatureVerificationException {
        return Webhook.constructEvent(payload, sigHeader, webhookSecret);
    }

    public String handleEvent(Event event) {
        return switch (event.getType()) {
            case "payment_intent.succeeded" ->
                    handlePaymentIntentSucceeded((PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null));
            case "checkout.session.completed" ->
                    handleCheckoutSessionCompleted((Session) event.getDataObjectDeserializer().getObject().orElse(null));
            case "invoice.paid" ->
                    handleInvoicePaid((Invoice) event.getDataObjectDeserializer().getObject().orElse(null));
            case "invoice.payment_failed" ->
                    handleInvoicePaymentFailed((Invoice) event.getDataObjectDeserializer().getObject().orElse(null));
            case "customer.subscription.created" ->
                    handleCustomerSubscriptionCreated((Subscription) event.getDataObjectDeserializer().getObject().orElse(null));
            case "customer.subscription.updated" ->
                    handleCustomerSubscriptionUpdated((Subscription) event.getDataObjectDeserializer().getObject().orElse(null));
            case "customer.subscription.deleted" ->
                    handleCustomerSubscriptionDeleted((Subscription) event.getDataObjectDeserializer().getObject().orElse(null));
            case "charge.refunded" ->
                    handleChargeRefund((Refund) event.getDataObjectDeserializer().getObject().orElse(null));
            default -> {
                log.warn("Unhandled Stripe event type: {}", event.getType());
                yield "Unhandled event type: " + event.getType();
            }
        };
    }

    private String handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        if (paymentIntent == null) {
            log.error("payment_intent.succeeded received with NULL data");
            return "Invalid payment_intent.succeeded";
        }

        log.info("PaymentIntent succeeded: {}, Amount={}, Currency={}",
                paymentIntent.getId(),
                paymentIntent.getAmount(),
                paymentIntent.getCurrency());

        return "PaymentIntent processed";
    }

    private String handleCheckoutSessionCompleted(Session session) {
        if (session == null) {
            log.error("checkout.session.completed received with NULL data");
            return "Invalid checkout.session.completed";
        }

        log.info("Checkout Session completed: {}, Customer={}, AmountTotal={}",
                session.getId(),
                session.getCustomer(),
                session.getAmountTotal());

        return "CheckoutSession processed";
    }

    private String handleInvoicePaid(Invoice invoice) {
        if (invoice == null) {
            log.error("invoice.paid received with NULL data");
            return "Invalid invoice.paid";
        }

        log.info("Invoice paid: {}, AmountDue={}, Customer={}",
                invoice.getId(),
                invoice.getAmountDue(),
                invoice.getCustomer());

        return "Invoice paid processed";
    }

    private String handleInvoicePaymentFailed(Invoice invoice) {
        if (invoice == null) {
            log.error("invoice.payment_failed received with NULL data");
            return "Invalid invoice.payment_failed";
        }

        log.warn("Invoice payment failed: {}, AmountDue={}, Customer={}",
                invoice.getId(),
                invoice.getAmountDue(),
                invoice.getCustomer());

        return "Invoice payment failed processed";
    }

    private String handleCustomerSubscriptionCreated(Subscription sub) {
        if (sub == null) {
            log.error("customer.subscription.created received with NULL data");
            return "Invalid subscription.created";
        }

        log.info("Subscription created: {}, Customer={}, Status={}",
                sub.getId(),
                sub.getCustomer(),
                sub.getStatus());

        return "Subscription created processed";
    }

    private String handleCustomerSubscriptionUpdated(Subscription sub) {
        if (sub == null) {
            log.error("customer.subscription.updated received with NULL data");
            return "Invalid subscription.updated";
        }

        log.info("Subscription updated: {}, Status={}, CancelAtPeriodEnd={}",
                sub.getId(),
                sub.getStatus(),
                sub.getCancelAtPeriodEnd());

        return "Subscription updated processed";
    }

    private String handleCustomerSubscriptionDeleted(Subscription sub) {
        if (sub == null) {
            log.error("customer.subscription.deleted received with NULL data");
            return "Invalid subscription.deleted";
        }

        log.warn("Subscription deleted: {}, Customer={}, Status={}",
                sub.getId(),
                sub.getCustomer(),
                sub.getStatus());

        return "Subscription deleted processed";
    }

    private String handleChargeRefund(Refund refund) {
        if (refund == null) {
            log.error("charge.refunded received with NULL data");
            return "Invalid charge.refunded";
        }

        log.info("Refund processed: {}, Amount={}, Status={}",
                refund.getId(),
                refund.getAmount(),
                refund.getStatus());

        return "Refund processed";
    }
}
