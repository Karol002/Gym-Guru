package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.domain.dto.exist.ExistExerciseDto;
import com.gymguru.domain.dto.insert.InsertExerciseDto;
import com.gymguru.service.ExerciseService;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseMapper {
    private final PlanService planService;

    public Exercise mapToExercise(final InsertExerciseDto insertExerciseDto) throws PlanNotFoundException {
        return new Exercise(
                insertExerciseDto.getName(),
                insertExerciseDto.getDescription(),
                insertExerciseDto.getSeriesQuantity(),
                insertExerciseDto.getRepetitionsQuantity(),
                planService.getPlan(insertExerciseDto.getPlanId())
        );
    }

    public  Exercise mapToExercise(final ExistExerciseDto existExerciseDto) throws PlanNotFoundException {
        return  new Exercise(
                existExerciseDto.getId(),
                existExerciseDto.getName(),
                existExerciseDto.getDescription(),
                existExerciseDto.getSeriesQuantity(),
                existExerciseDto.getRepetitionsQuantity(),
                planService.getPlan(existExerciseDto.getPlanId())
        );
    }

    public ExistExerciseDto mapToExistExerciseDto(final Exercise exercise) {
        return new ExistExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getSeriesQuantity(),
                exercise.getRepetitionsQuantity(),
                exercise.getPlan().getId()
        );
    }

    public List<ExistExerciseDto> mapToExistExerciseDtoList(List<Exercise> exercises) {
        return exercises.stream()
                .map(this::mapToExistExerciseDto)
                .toList();
    }
}

