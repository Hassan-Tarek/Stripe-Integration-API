package com.stripe.integration.service;

import com.stripe.exception.StripeException;
import com.stripe.integration.dto.request.SubscriptionRequest;
import com.stripe.integration.dto.response.SubscriptionResponse;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCancelParams;
import com.stripe.param.SubscriptionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    public SubscriptionResponse createSubscription(SubscriptionRequest request) throws StripeException {
        SubscriptionCreateParams.Item item = SubscriptionCreateParams.Item.builder()
                .setPrice(request.getPriceId())
                .build();

        SubscriptionCreateParams.Builder paramsBuilder = SubscriptionCreateParams.builder()
                .setCustomer(request.getCustomerId())
                .addItem(item)
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE);

        if (request.getPaymentMethodId() != null) {
            paramsBuilder.setDefaultPaymentMethod(request.getPaymentMethodId());
        }

        if (request.getTrialDays() != null) {
            paramsBuilder.setTrialPeriodDays(request.getTrialDays());
        }

        Subscription subscription = Subscription.create(paramsBuilder.build());
        return mapToSubscriptionResponse(subscription);
    }

    public SubscriptionResponse getSubscription(String subscriptionId) throws StripeException {
        Subscription subscription = Subscription.retrieve(subscriptionId);
        return mapToSubscriptionResponse(subscription);
    }

    public SubscriptionResponse cancelSubscription(String subscriptionId) throws StripeException {
        Subscription subscription = Subscription.retrieve(subscriptionId);
        SubscriptionCancelParams cancelParams = SubscriptionCancelParams.builder()
                .build();
        subscription = subscription.cancel(cancelParams);
        return mapToSubscriptionResponse(subscription);
    }

    private SubscriptionResponse mapToSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getEndedAt(),
                subscription.getLatestInvoice()
        );
    }
}
