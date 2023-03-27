package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.dto.internal.exist.ExistPlanDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertPlanDto;
import com.v1.gymguru.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PlanMapper {
    private final UserService userService;

    public Plan mapToPlan(final InsertPlanDto insertPlanDto) throws UserNotFoundException {
        return new Plan(
                insertPlanDto.getDescription(),
                userService.getUser(insertPlanDto.getUserId())
        );
    }

    public Plan mapToPlan(final ExistPlanDto existPlanDto) throws UserNotFoundException {
        return new Plan(
                existPlanDto.getId(),
                existPlanDto.getDescription(),
                userService.getUser(existPlanDto.getId())
        );
    }

    public ExistPlanDto mapToExistPlanDto(final Plan plan) {
        return new ExistPlanDto(
                plan.getId(),
                plan.getDescription(),
                plan.getUser().getId()
        );
    }

    public List<ExistPlanDto> mapToExistPlanDtoList(List<Plan> plans) {
        return plans.stream()
                .map(this::mapToExistPlanDto)
                .toList();
    }
}
