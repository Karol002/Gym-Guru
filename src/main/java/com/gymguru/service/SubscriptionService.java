package com.gymguru.service;

import com.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.domain.Subscription;
import com.gymguru.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Subscription> getSubscriptionsWithPlanByTrainerId(Long trainerId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByTrainerId(trainerId);
        List<Plan> plans = planService.getAllPlansByTrainerId(trainerId);

        List<Subscription> subscriptionsWithPlan = new ArrayList<>();
        for (Plan plan : plans) {
            for (Subscription subscription : subscriptions) {
                if (subscription.getUser().equals(plan.getUser())) {
                    subscriptionsWithPlan.add(subscription);
                }
            }
        }

        return subscriptionsWithPlan;
    }

    public Subscription getSubscriptionByUserId(Long userId) throws SubscriptionNotFoundException {
        return subscriptionRepository.findByUserId(userId).orElseThrow(SubscriptionNotFoundException::new);
    }

    public Subscription createSubscription(final Subscription subscription) {
        if (!isSubscriptionActive(subscription.getUser().getId())) {
            return subscriptionRepository.save(subscription);
        } else throw new RuntimeException();
    }

    public void extendSubscription(Long userId, Long monthQuantity) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findByUserId(userId).orElseThrow(SubscriptionNotFoundException::new);
        subscription.setEndDate(subscription.getEndDate().plusMonths(monthQuantity));
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscriptionById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public boolean isSubscriptionActive(Subscription subscription) {
        return subscription.getEndDate().isAfter(LocalDate.now())
                || subscription.getEndDate().isEqual(LocalDate.now());
    }

    public boolean isSubscriptionActive(Long userId) {
        Optional<Subscription> subscription = subscriptionRepository.findByUserId(userId);
        return subscription.filter(value -> value.getEndDate().isAfter(LocalDate.now())
                || value.getEndDate().isEqual(LocalDate.now())).isPresent();
    }
}
