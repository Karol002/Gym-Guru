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

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Meal getMeal(Long id) throws MealNotFoundException {
        return mealRepository.findById(id).orElseThrow(MealNotFoundException::new);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public Meal saveMeal(final Meal meal) {
        return mealRepository.save(meal);
    }
}
