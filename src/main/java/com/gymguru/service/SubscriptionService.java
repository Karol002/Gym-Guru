package com.gymguru.service;

import com.gymguru.controller.exception.single.InCorrectSubscriptionDataException;
import com.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.domain.Subscription;
import com.gymguru.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanService planService;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByTrainerId(Long trainerId) {
        return subscriptionRepository.findAllByTrainerId(trainerId);
    }

    @Transactional
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

    @Transactional
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

    public Subscription createSubscription(Subscription subscription) throws InCorrectSubscriptionDataException {
        if (checkSubscriptionData(subscription)) {
            subscription.setPrice(calculatePrice(subscription.getStartDate(), subscription.getEndDate(), subscription.getTrainer().getMonthPrice()));
            return subscriptionRepository.save(subscription);
        } else throw new InCorrectSubscriptionDataException();
    }

    public boolean checkSubscriptionData(Subscription subscription) {
        return (!subscriptionRepository.existsByUserId(subscription.getUser().getId())
                && checkIsCorrectLong(subscription.getStartDate(), subscription.getEndDate()));
    }

    public BigDecimal calculatePrice(LocalDate startDate, LocalDate endDate, BigDecimal monthPrice) {
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
        return new BigDecimal(monthPrice.longValue() * monthsBetween);
    }

    public boolean checkIsCorrectLong(LocalDate startDate, LocalDate endDate) {
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
        return (monthsBetween > 0 && monthsBetween <= 6);
    }

    @Transactional
    public Subscription extendSubscription(Long userId, Long monthQuantity) throws SubscriptionNotFoundException, InCorrectSubscriptionDataException {
        Subscription subscription = subscriptionRepository.findByUserId(userId).orElseThrow(SubscriptionNotFoundException::new);
        if (checkIsCorrectLong(subscription.getStartDate(), subscription.getStartDate().plusMonths(monthQuantity))) {

            BigDecimal extendPrice = calculatePrice(subscription.getStartDate(), subscription.getEndDate().plusMonths(monthQuantity),
                    subscription.getTrainer().getMonthPrice());
            subscription.setPrice(extendPrice);
            subscription.setEndDate(subscription.getEndDate().plusMonths(monthQuantity));

            return subscriptionRepository.save(subscription);
        } else throw new InCorrectSubscriptionDataException();
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
