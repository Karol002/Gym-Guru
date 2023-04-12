package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Exercise;
import com.v1.gymguru.domain.dto.ExerciseDto;
import com.v1.gymguru.domain.dto.save.SaveExerciseDto;
import com.v1.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseMapper {
    private final PlanService planService;

    public  Exercise mapToExercise(final ExerciseDto exerciseDto) throws PlanNotFoundException {
        return  new Exercise(
                exerciseDto.getId(),
                exerciseDto.getName(),
                exerciseDto.getDescription(),
                exerciseDto.getSeriesQuantity(),
                exerciseDto.getRepetitionsQuantity(),
                planService.getPlan(exerciseDto.getPlanId())
        );
    }

    public ExerciseDto mapToExistExerciseDto(final Exercise exercise) {
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getSeriesQuantity(),
                exercise.getRepetitionsQuantity(),
                exercise.getPlan().getId()
        );
    }

    public List<ExerciseDto> mapToExistExerciseDtoList(List<Exercise> exercises) {
        return exercises.stream()
                .map(this::mapToExistExerciseDto)
                .toList();
    }
}

