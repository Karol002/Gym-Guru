package com.v1.gymguru.adapter.plan;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Exercise;
import com.v1.gymguru.domain.Meal;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.dto.save.SaveExerciseDto;
import com.v1.gymguru.domain.dto.save.SaveMealDto;
import com.v1.gymguru.service.TrainerService;
import com.v1.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanAdapter {
    private final UserService userService;
    private final TrainerService trainerService;

    public Plan toPlan(CompletePlanDto completePlanDto) throws TrainerNotFoundException, UserNotFoundException {
        Plan plan = new Plan(
                completePlanDto.getDietDescription(),
                completePlanDto.getExerciseDescription(),
                userService.getUserById(completePlanDto.getUserId()),
                trainerService.getTrainerById(completePlanDto.getTrainerId())
        );
        List<Exercise> exercises = toExercises(completePlanDto.getSaveExerciseDtos(), plan);
        List<Meal> meals = toMeals(completePlanDto.getSaveMealDtos(), plan);

        plan.setExercises(exercises);
        plan.setMeals(meals);

        return plan;
    }

    private List<Exercise> toExercises(List<SaveExerciseDto> exerciseDtos, Plan plan) {
        return exerciseDtos.stream()
                .map(dto -> toExercise(dto, plan))
                .collect(Collectors.toList());
    }

    private Exercise toExercise(SaveExerciseDto saveExerciseDto, Plan plan) {
        return new Exercise(
                saveExerciseDto.getName(),
                saveExerciseDto.getDescription(),
                saveExerciseDto.getSeriesQuantity(),
                saveExerciseDto.getRepetitionsQuantity(),
                plan
        );
    }
    private List<Meal> toMeals(List<SaveMealDto> mealDtos, Plan plan) {
        return mealDtos.stream()
                .map(dto -> toMeal(dto, plan))
                .collect(Collectors.toList());
    }


    private Meal toMeal(SaveMealDto saveMealDto, Plan plan) {
        return new Meal(
                saveMealDto.getName(),
                saveMealDto.getCookInstruction(),
                plan
        );
    }
}
