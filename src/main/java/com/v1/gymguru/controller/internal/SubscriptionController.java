package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.domain.dto.SubscriptionDto;
import com.v1.gymguru.domain.dto.save.SaveSubscriptionDto;
import com.v1.gymguru.mapper.SubscriptionMapper;
import com.v1.gymguru.service.SubscriptionService;
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
        subscriptionService.saveSubscription(subscription);
        return ResponseEntity.ok().build();
    }

    /*@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subscription> extendSubscription(@RequestBody SubscriptionDto subscriptionDto) throws UserNotFoundException, TrainerNotFoundException, SubscriptionNotFoundException {
        Subscription subscription = subscriptionMapper.mapToSubscription(subscriptionDto);
        return ResponseEntity.ok(subscriptionService.updateSubscription(subscription));
    }*/
}
