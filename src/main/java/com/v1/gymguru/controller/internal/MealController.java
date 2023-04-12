package com.v1.gymguru.controller.internal;

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
/*
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMeal(@RequestBody SaveMealDto saveMealDto) throws PlanNotFoundException {
        Meal meal = mealMapper.mapToMeal(saveMealDto);
        mealService.saveMeal(meal);
        return ResponseEntity.ok().build();
    }*/
}
