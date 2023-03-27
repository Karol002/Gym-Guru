package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.MealNotFoundException;
import com.v1.gymguru.domain.Meal;
import com.v1.gymguru.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MealService {
    private final MealRepository mealRepository;

    public List<Meal> getMealsByPlanId(Long planId) {
        return mealRepository.findAllByPlanId(planId);
    }

    public Meal saveMeal(final Meal meal) {
        return mealRepository.save(meal);
    }
}
