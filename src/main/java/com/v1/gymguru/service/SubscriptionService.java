package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanService planService;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByTrainerId(Long trainerId) {
        return subscriptionRepository.findAllByTrainerId(trainerId);
    }

    public Subscription getSubscriptionByUserId(Long userId) throws SubscriptionNotFoundException {
        return subscriptionRepository.findByUserId(userId).orElseThrow(SubscriptionNotFoundException::new);
    }

    public Subscription saveSubscription(final Subscription subscription) throws SubscriptionNotFoundException {
        if (!isSubscriptionActive(subscription.getUser().getId())) {
            return subscriptionRepository.save(subscription);
        } else throw new RuntimeException();
    }

    public void deleteSubscriptionById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public boolean isSubscriptionActive(Subscription subscription) {
        return subscription.getEndDate().isAfter(LocalDate.now()) || subscription.getEndDate().isEqual(LocalDate.now());
    }

    public boolean isSubscriptionActive(Long userId) throws SubscriptionNotFoundException {
        Optional<Subscription> subscription = subscriptionRepository.findByUserId(userId);
        return subscription.filter(value -> value.getEndDate().isAfter(LocalDate.now()) || value.getEndDate().isEqual(LocalDate.now())).isPresent();
        //subscription.filter(value -> !value.getEndDate().isBefore(LocalDate.now())).isPresent();
    }

    public List<Subscription> getSubscriptionsWithoutPlanByTrainerId(Long trainerId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByTrainerId(trainerId);
        List<Plan> plans = planService.getAllPlansByTrainerId(trainerId);

        List<Subscription> subscriptionsWithoutPlan = new ArrayList<>(subscriptions);

        for (Plan plan : plans) {
            for (Subscription subscription : subscriptions) {
                if (subscription.getUser().equals(plan.getUser())) {
                    subscriptionsWithoutPlan.remove(subscription);
                }
            }
        }

        return subscriptionsWithoutPlan;
    }

 /*   public Subscription extendSubscription(final Subscription subscription) throws SubscriptionNotFoundException {
        if (subscriptionRepository.existsById(subscription.getId())) {
            return subscriptionRepository.save(subscription);
        } else throw new SubscriptionNotFoundException();
    }*/
}
