package com.stripe.integration.service;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.CheckoutSessionRequest;
import com.stripe.integration.dto.response.CheckoutSessionResponse;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutSessionService {

    public CheckoutSessionResponse createCheckoutSession(CheckoutSessionRequest request) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        request.getItems().forEach(item -> {
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(item.getProductName())
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setProductData(productData)
                            .setCurrency(request.getCurrency())
                            .setUnitAmount(item.getAmount())
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(item.getQuantity())
                            .setPriceData(priceData)
                            .build();

            lineItems.add(lineItem);
        });

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(request.getSuccessUrl())
                .setCancelUrl(request.getCancelUrl())
                .addAllLineItem(lineItems)
                .build();

        Session session =  Session.create(params);
        return mapToCheckoutSession(session);
    }

    public CheckoutSessionResponse getCheckoutSession(String checkoutSessionId) throws StripeException {
        Session session = Session.retrieve(checkoutSessionId);
        return mapToCheckoutSession(session);
    }

    private CheckoutSessionResponse mapToCheckoutSession(Session session) {
        return new CheckoutSessionResponse(
                session.getId(),
                session.getUrl(),
                session.getAmountTotal(),
                session.getCurrency(),
                session.getPaymentStatus(),
                session.getExpiresAt()
        );
    }
}
