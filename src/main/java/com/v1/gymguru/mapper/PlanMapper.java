package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.dto.PlanDto;
import com.v1.gymguru.domain.dto.save.SavePlanDto;
import com.v1.gymguru.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PlanMapper {
    private final UserService userService;

    public Plan mapToPlan(final SavePlanDto savePlanDto) throws UserNotFoundException {
        return new Plan(
                savePlanDto.getDescription(),
                userService.getUserById(savePlanDto.getUserId())
        );
    }

    public Plan mapToPlan(final PlanDto planDto) throws UserNotFoundException {
        return new Plan(
                planDto.getId(),
                planDto.getDescription(),
                userService.getUserById(planDto.getId())
        );
    }

    public PlanDto mapToExistPlanDto(final Plan plan) {
        return new PlanDto(
                plan.getId(),
                plan.getDescription(),
                plan.getUser().getId()
        );
    }

    public List<PlanDto> mapToExistPlanDtoList(List<Plan> plans) {
        return plans.stream()
                .map(this::mapToExistPlanDto)
                .toList();
    }
}
