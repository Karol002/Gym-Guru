package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Meal;
import com.v1.gymguru.domain.dto.MealDto;
import com.v1.gymguru.domain.dto.save.SaveMealDto;
import com.v1.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MealMapper {
    private final PlanService planService;


    public Meal mapToMeal(final SaveMealDto saveMealDto) throws PlanNotFoundException {
        return new Meal(
                saveMealDto.getName(),
                saveMealDto.getCookInstruction(),
                planService.getPlan(saveMealDto.getPlanId())
        );
    }

    public Meal mapToMeal(final MealDto mealDto) throws PlanNotFoundException {
        return new Meal(
                mealDto.getId(),
                mealDto.getName(),
                mealDto.getCookInstruction(),
                planService.getPlan(mealDto.getPlanId())
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
