package com.v1.gymguru.controller;

import com.v1.gymguru.controller.exception.single.MealNotFoundException;
import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Meal;
import com.v1.gymguru.domain.dto.MealDto;
import com.v1.gymguru.domain.dto.save.SaveMealDto;
import com.v1.gymguru.mapper.MealMapper;
import com.v1.gymguru.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/meals")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MealController {
    private final MealService mealService;
    private final MealMapper mealMapper;

    @GetMapping(value = "plan/{planId}")
    public ResponseEntity<List<MealDto>> getMealsByPlanId(@PathVariable Long planId) {
        List<Meal> exercises = mealService.getMealsByPlanId(planId);
        return ResponseEntity.ok(mealMapper.mapToExistMealDtoList(exercises));
    }

    @PutMapping
    public ResponseEntity<Void> updateMeal(@RequestBody MealDto mealDto) throws PlanNotFoundException, MealNotFoundException {
        Meal meal = mealMapper.mapToMeal(mealDto);
        mealService.updateMeal(meal);
        return ResponseEntity.ok().build();
    }
}
