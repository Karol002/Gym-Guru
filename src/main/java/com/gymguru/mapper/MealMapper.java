package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Meal;
import com.gymguru.domain.dto.MealDto;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MealMapper {
    private final PlanService planService;

    public Meal mapToMeal(final MealDto mealDto) throws PlanNotFoundException {
        return new Meal(
                mealDto.getId(),
                mealDto.getName(),
                mealDto.getCookInstruction(),
                planService.getPlanById(mealDto.getPlanId())
        );
    }

    public MealDto mapToExistMealDto(final Meal meal) {
        return new MealDto(
                meal.getId(),
                meal.getName(),
                meal.getCookInstruction(),
                meal.getPlan().getId()
        );
    }

    public List<MealDto> mapToExistMealDtoList(List<Meal> meals) {
        return meals.stream()
                .map(this::mapToExistMealDto)
                .toList();
    }
}
