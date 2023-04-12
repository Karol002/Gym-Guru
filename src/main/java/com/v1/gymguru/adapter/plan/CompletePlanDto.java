package com.v1.gymguru.adapter.plan;

import com.v1.gymguru.domain.dto.save.SaveExerciseDto;
import com.v1.gymguru.domain.dto.save.SaveMealDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompletePlanDto {
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
    private List<SaveExerciseDto> saveExerciseDtos;
    private List<SaveMealDto> saveMealDtos;
}
