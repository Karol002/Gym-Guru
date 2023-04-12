package com.v1.gymguru.domain.dto.save;

import com.v1.gymguru.domain.dto.ExerciseDto;
import com.v1.gymguru.domain.dto.MealDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SavePlanDto {
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
    private List<SaveExerciseDto> exerciseDtos;
    private List<SaveMealDto> mealDtos;
}
