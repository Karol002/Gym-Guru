package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.domain.dto.ExerciseDto;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExerciseMapper {
    private final PlanService planService;

    public  Exercise mapToExercise(final ExerciseDto exerciseDto) throws PlanNotFoundException {
        return  new Exercise(
                exerciseDto.getId(),
                exerciseDto.getName(),
                exerciseDto.getDescription(),
                exerciseDto.getSeriesQuantity(),
                exerciseDto.getRepetitionsQuantity(),
                planService.getPlanById(exerciseDto.getPlanId())
        );
    }

    public ExerciseDto mapToExerciseDto(final Exercise exercise) {
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getSeriesQuantity(),
                exercise.getRepetitionsQuantity(),
                exercise.getPlan().getId()
        );
    }

    public List<ExerciseDto> mapToExerciseDtoList(List<Exercise> exercises) {
        return exercises.stream()
                .map(this::mapToExerciseDto)
                .toList();
    }
}

