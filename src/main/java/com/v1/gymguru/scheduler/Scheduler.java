package com.v1.gymguru.scheduler;

import com.v1.gymguru.config.EmailConfig;
import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.domain.Mail;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.service.CredentialService;
import com.v1.gymguru.service.SimpleEmailService;
import com.v1.gymguru.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final SubscriptionService subscriptionService;
    private final SimpleEmailService simpleEmailService;
    private final CredentialService credentialService;

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 10000)
    public void checkSubscriptions() throws CredentialNotFoundException {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        for (Subscription subscription : subscriptions) {
            if (subscriptionService.isSubscriptionActive(subscription)) thankForSubscription(subscription);
            else deleteSubscription(subscription);
        }
    }

    private void thankForSubscription(Subscription subscription) throws CredentialNotFoundException {
        simpleEmailService.send(new Mail(
                credentialService.getById(subscription.getUser().getCredential().getId()).getEmail(),
                SimpleEmailService.SUBJECT,
                SimpleEmailService.SUBSCRIPTION_ACTIVE
        ));
    }

    private void deleteSubscription(Subscription subscription) throws CredentialNotFoundException {
        simpleEmailService.send(new Mail(
                credentialService.getById(subscription.getUser().getCredential().getId()).getEmail(),
                SimpleEmailService.SUBJECT,
                SimpleEmailService.SUBSCRIPTION_EXPIRED
        ));
        subscriptionService.deleteSubscriptionById(subscription.getId());
    }

}
