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
@RequestMapping("/v1/gymguru/subscription")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @GetMapping(value = "trainer/{trainerId}")
    public ResponseEntity<List<ExistSubscriptionDto>> getSubscriptions(@PathVariable Long trainerId) {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();//To DO
        return ResponseEntity.ok(subscriptionMapper.mapToSubscriptionDtoList(subscriptions));
    }

    @GetMapping(value = "user/{userId}")
    public ResponseEntity<ExistSubscriptionDto> getSubscription(@PathVariable Long userId) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionService.getSubscription(userId);//To DO pobierz subskrypcje po id usera
        return ResponseEntity.ok(subscriptionMapper.mapToExistSubscriptionDto(subscription));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPlan(@RequestBody InsertSubscriptionDto insertSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        Subscription subscription = subscriptionMapper.mapToSubscription(insertSubscriptionDto);
        subscriptionService.saveSubscription(subscription);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok().build();
    }
}
