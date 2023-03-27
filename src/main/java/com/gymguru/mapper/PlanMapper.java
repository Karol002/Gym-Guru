package com.gymguru.mapper;

import com.gymguru.domain.Plan;
import com.gymguru.domain.dto.internal.exist.ExistPlanDto;
import com.gymguru.domain.dto.internal.insert.InsertPlanDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanMapper {

    public Plan mapToPlan(final InsertPlanDto insertPlanDto) {
        return new Plan(
                insertPlanDto.getDescription()
        );
    }

    public Plan mapToPlan(final ExistPlanDto existPlanDto) {
        return new Plan(
                existPlanDto.getId(),
                existPlanDto.getDescription()
        );
    }

    public ExistPlanDto mapToExistPlanDto(final Plan plan) {
        return new ExistPlanDto(
                plan.getId(),
                plan.getDescription()
        );
    }

    public List<ExistPlanDto> mapToExistPlanDtoList(List<Plan> plans) {
        return plans.stream()
                .map(this::mapToExistPlanDto)
                .toList();
    }
}
