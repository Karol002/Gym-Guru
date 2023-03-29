package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptionsByTrainerId(Long trainerId) {
        return subscriptionRepository.findAllByTrainerId(trainerId);
    }

    public Subscription getSubscriptionByUserId(Long userId) throws SubscriptionNotFoundException {
        return subscriptionRepository.findByUserId(userId).orElseThrow(SubscriptionNotFoundException::new);
    }

    public Subscription saveSubscription(final Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(final Subscription subscription) throws SubscriptionNotFoundException {
        if (subscriptionRepository.existsById(subscription.getId())) {
            return subscriptionRepository.save(subscription);
        } else throw new SubscriptionNotFoundException();
    }

    public boolean isSubscriptionActive(Long userId) throws SubscriptionNotFoundException {
        Subscription subscription = getSubscriptionByUserId(userId);
        return subscription.getEndDate().isBefore(LocalDate.now());
    }
}
