package com.gymguru.controller.internal;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Meal;
import com.gymguru.domain.dto.internal.exist.ExistMealDto;
import com.gymguru.domain.dto.internal.insert.InsertMealDto;
import com.gymguru.mapper.MealMapper;
import com.gymguru.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/meal")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MealController {
    private final MealService mealService;
    private final MealMapper mealMapper;

    @GetMapping
    public ResponseEntity<List<ExistMealDto>> getAllMeals() {
        List<Meal> exercises = mealService.getAllMeals();
        return ResponseEntity.ok(mealMapper.mapToExistMealDtoList(exercises));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addMeal(@RequestBody InsertMealDto insertMealDto) throws PlanNotFoundException {
        Meal meal = mealMapper.mapToMeal(insertMealDto);
        mealService.saveMeal(meal);
        return ResponseEntity.ok().build();
    }
}
