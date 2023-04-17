package com.gymguru.domain.dto.save;

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
