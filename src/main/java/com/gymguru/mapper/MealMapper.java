package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Meal;
import com.gymguru.domain.dto.internal.exist.ExistMealDto;
import com.gymguru.domain.dto.internal.insert.InsertMealDto;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MealMapper {
    private final PlanService planService;


    public Meal mapToMeal(final InsertMealDto insertMealDto) throws PlanNotFoundException {
        return new Meal(
                insertMealDto.getName(),
                insertMealDto.getCookInstruction(),
                planService.getPlan(insertMealDto.getPlanId())
        );
    }

    public Meal mapToMeal(final ExistMealDto existMealDto) throws PlanNotFoundException {
        return new Meal(
                existMealDto.getId(),
                existMealDto.getName(),
                existMealDto.getCookInstruction(),
                planService.getPlan(existMealDto.getPlanId())
        );
    }

    public ExistMealDto mapToExistMealDto(final Meal meal) {
        return new ExistMealDto(
                meal.getId(),
                meal.getName(),
                meal.getCookInstruction(),
                meal.getPlan().getId()
        );
    }

    public List<ExistMealDto> mapToExistMealDtoList(List<Meal> meals) {
        return meals.stream()
                .map(this::mapToExistMealDto)
                .toList();
    }
}
