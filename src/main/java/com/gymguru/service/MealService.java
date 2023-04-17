package com.gymguru.service;

import com.gymguru.controller.exception.single.MealNotFoundException;
import com.gymguru.domain.Meal;
import com.gymguru.repository.MealRepository;
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

    public Meal updateMeal(final Meal meal) throws MealNotFoundException {
        if (mealRepository.existsById(meal.getId())) {
            return mealRepository.save(meal);
        } else throw new MealNotFoundException();
    }
}
