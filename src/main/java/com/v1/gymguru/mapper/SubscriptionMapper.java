package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Subscription;
import com.v1.gymguru.domain.dto.SubscriptionDto;
import com.v1.gymguru.domain.dto.save.SaveSubscriptionDto;
import com.v1.gymguru.service.TrainerService;
import com.v1.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionMapper {
    private final UserService userService;
    private final TrainerService trainerService;

    public Subscription mapToSubscription(final SaveSubscriptionDto saveSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        return new Subscription(
                saveSubscriptionDto.getPrice(),
                saveSubscriptionDto.getStartDate(),
                saveSubscriptionDto.getEndDate(),
                userService.getUserById(saveSubscriptionDto.getUserId()),
                trainerService.getTrainerById(saveSubscriptionDto.getTrainerId())
        );
    }

    public Subscription mapToSubscription(final SubscriptionDto subscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        return new Subscription(
                subscriptionDto.getId(),
                subscriptionDto.getPrice(),
                subscriptionDto.getStartDate(),
                subscriptionDto.getEndDate(),
                userService.getUserById(subscriptionDto.getUserId()),
                trainerService.getTrainerById(subscriptionDto.getTrainerId())
        );
    }

    public SubscriptionDto mapToExistSubscriptionDto(final Subscription subscription) {
        return new SubscriptionDto(
                subscription.getId(),
                subscription.getPrice(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getUser().getId(),
                subscription.getTrainer().getId()
        );
    }

    public List<SubscriptionDto> mapToExistSubscriptionDtoList(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(this::mapToExistSubscriptionDto)
                .toList();
    }
}
