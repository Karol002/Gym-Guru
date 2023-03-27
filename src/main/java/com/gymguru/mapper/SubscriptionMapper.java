package com.gymguru.mapper;

import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Subscription;
import com.gymguru.domain.dto.internal.exist.ExistSubscriptionDto;
import com.gymguru.domain.dto.internal.insert.InsertSubscriptionDto;
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

    public Subscription mapToSubscription(final InsertSubscriptionDto insertSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        return new Subscription(
                insertSubscriptionDto.getPrice(),
                insertSubscriptionDto.getStartDate(),
                insertSubscriptionDto.getEndDate(),
                userService.getUser(insertSubscriptionDto.getUserId()),
                trainerService.getTrainer(insertSubscriptionDto.getTrainerId())
        );
    }

    public Subscription mapToSubscription(final ExistSubscriptionDto existSubscriptionDto) throws UserNotFoundException, TrainerNotFoundException {
        return new Subscription(
                existSubscriptionDto.getId(),
                existSubscriptionDto.getPrice(),
                existSubscriptionDto.getStartDate(),
                existSubscriptionDto.getEndDate(),
                userService.getUser(existSubscriptionDto.getUserId()),
                trainerService.getTrainer(existSubscriptionDto.getTrainerId())
        );
    }

    public ExistSubscriptionDto mapToExistSubscriptionDto(final Subscription subscription) {
        return new ExistSubscriptionDto(
                subscription.getId(),
                subscription.getPrice(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getUser().getId(),
                subscription.getTrainer().getId()
        );
    }

    public List<ExistSubscriptionDto> mapToSubscriptionDtoList(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .map(this::mapToExistSubscriptionDto)
                .toList();
    }
}
