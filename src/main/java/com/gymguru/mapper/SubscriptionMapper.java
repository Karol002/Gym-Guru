package com.gymguru.mapper;

import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Subscription;
import com.gymguru.domain.dto.SubscriptionDto;
import com.gymguru.domain.dto.save.SaveSubscriptionDto;
import com.gymguru.service.TrainerService;
import com.gymguru.service.UserService;
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
                saveSubscriptionDto.getStartDate(),
                saveSubscriptionDto.getEndDate(),
                userService.getUserById(saveSubscriptionDto.getUserId()),
                trainerService.getTrainerById(saveSubscriptionDto.getTrainerId())
        );
    }

    public SubscriptionDto mapToSubscriptionDto(final Subscription subscription) {
        return new SubscriptionDto(
                subscription.getId(),
                subscription.getPrice(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getUser().getId(),
                subscription.getTrainer().getId()
        );
    }

    public List<SubscriptionDto> mapToSubscriptionDtoList(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(this::mapToSubscriptionDto)
                .toList();
    }
}
