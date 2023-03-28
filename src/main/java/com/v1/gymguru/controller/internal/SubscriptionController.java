package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.domain.dto.internal.exist.ExistSubscriptionDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertSubscriptionDto;
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
    public ResponseEntity<List<ExistSubscriptionDto>> getSubscriptions(@PathVariable Long trainerId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByTrainerId(trainerId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDtoList(subscriptions));
    }

    @GetMapping(value = "user/{userId}")
    public ResponseEntity<ExistSubscriptionDto> getSubscription(@PathVariable Long userId) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionService.getSubscriptionByUserId(userId);
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDto(subscription));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSubscription(@RequestBody InsertSubscriptionDto insertSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        Subscription subscription = subscriptionMapper.mapToSubscription(insertSubscriptionDto);
        subscriptionService.saveSubscription(subscription);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subscription> extendSubscription(@RequestBody ExistSubscriptionDto existSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        Subscription subscription = subscriptionMapper.mapToSubscription(existSubscriptionDto);
        return ResponseEntity.ok(subscriptionService.saveSubscription(subscription));
    }
}
