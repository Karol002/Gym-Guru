package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscription(Long id) throws SubscriptionNotFoundException {
        return subscriptionRepository.findById(id).orElseThrow(SubscriptionNotFoundException::new);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public Subscription saveSubscription(final Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
