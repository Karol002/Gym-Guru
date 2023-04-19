package com.gymguru.scheduler;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.domain.Mail;
import com.gymguru.domain.Subscription;
import com.gymguru.service.CredentialService;
import com.gymguru.service.EmailService;
import com.gymguru.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final SubscriptionService subscriptionService;
    private final EmailService emailService;
    private final CredentialService credentialService;

    @Transactional
    @Scheduled(cron = "0 0 10 * * *")
    public void controlSubscriptions() throws CredentialNotFoundException {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        for (Subscription subscription : subscriptions) {
            if (subscriptionService.isSubscriptionActive(subscription)) thankForSubscription(subscription);
            else deleteSubscription(subscription);
        }
    }

    private void thankForSubscription(Subscription subscription) throws CredentialNotFoundException {
        emailService.send(new Mail(
                credentialService.getById(subscription.getUser().getCredential().getId()).getEmail(),
                EmailService.SUBJECT,
                EmailService.SUBSCRIPTION_ACTIVE
        ));
    }

    private void deleteSubscription(Subscription subscription) throws CredentialNotFoundException {
        emailService.send(new Mail(
                credentialService.getById(subscription.getUser().getCredential().getId()).getEmail(),
                EmailService.SUBJECT,
                EmailService.SUBSCRIPTION_EXPIRED
        ));
        subscriptionService.deleteSubscriptionById(subscription.getId());
    }

}
