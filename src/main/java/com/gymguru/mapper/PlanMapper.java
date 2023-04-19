package com.gymguru.mapper;

import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.domain.dto.PlanDto;
import com.gymguru.service.TrainerService;
import com.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanMapper {
    private final UserService userService;
    private final TrainerService trainerService;

    public Plan mapToPlan(final PlanDto planDto) throws UserNotFoundException, TrainerNotFoundException {
        return new Plan(
                planDto.getId(),
                planDto.getDietDescription(),
                planDto.getExerciseDescription(),
                userService.getUserById(planDto.getUserId()),
                trainerService.getTrainerById(planDto.getTrainerId())
        );
    }

    public PlanDto mapToPlanDto(final Plan plan) {
        return new PlanDto(
                plan.getId(),
                plan.getDietDescription(),
                plan.getExerciseDescription(),
                plan.getUser().getId(),
                plan.getTrainer().getId()
        );
    }
}
