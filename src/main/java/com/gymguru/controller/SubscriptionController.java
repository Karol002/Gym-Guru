package com.gymguru.controller;

import com.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.dto.SubscriptionDto;
import com.gymguru.domain.dto.save.SaveSubscriptionDto;
import com.gymguru.domain.Subscription;
import com.gymguru.mapper.SubscriptionMapper;
import com.gymguru.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/subscriptions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @GetMapping(value = "trainer/{trainerId}")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(@PathVariable Long trainerId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByTrainerId(trainerId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDtoList(subscriptions));
    }

    @GetMapping(value = "without/plan/{trainerId}")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsWithoutPlan(@PathVariable Long trainerId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsWithoutPlanByTrainerId(trainerId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDtoList(subscriptions));
    }

    @GetMapping(value = "with/plan/{trainerId}")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsWithPlan(@PathVariable Long trainerId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsWithPlanByTrainerId(trainerId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDtoList(subscriptions));
    }

    @GetMapping(value = "user/{userId}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Long userId) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionService.getSubscriptionByUserId(userId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDto(subscription));
    }

    @GetMapping(value = "active/{userId}")
    public ResponseEntity<Boolean> checkSubscriptionStatus(@PathVariable Long userId) throws SubscriptionNotFoundException {
        return ResponseEntity.ok(subscriptionService.isSubscriptionActive(userId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSubscription(@RequestBody SaveSubscriptionDto saveSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException, SubscriptionNotFoundException {
        Subscription subscription = subscriptionMapper.mapToSubscription(saveSubscriptionDto);
        subscriptionService.createSubscription(subscription);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/extend/{userId}/{monthQuantity}")
    public ResponseEntity<Void> extendSubscription(@PathVariable Long userId, @PathVariable Long monthQuantity) throws SubscriptionNotFoundException {
        subscriptionService.extendSubscription(userId, monthQuantity);
        return ResponseEntity.ok().build();
    }
}