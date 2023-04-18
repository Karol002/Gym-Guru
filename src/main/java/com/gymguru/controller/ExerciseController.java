package com.gymguru.controller;

import com.gymguru.controller.exception.single.ExerciseNotFoundException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.domain.dto.ExerciseDto;
import com.gymguru.mapper.ExerciseMapper;
import com.gymguru.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/exercises")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @GetMapping(value = "/plan/{planId}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByPlanId(@PathVariable Long planId) {
        List<Exercise> exercises = exerciseService.getExercisesByPlanId(planId);
        return ResponseEntity.ok(exerciseMapper.mapToExerciseDtoList(exercises));
    }

    @PutMapping()
    public ResponseEntity<ExerciseDto> updateExercise(@RequestBody ExerciseDto exerciseDto) throws PlanNotFoundException, ExerciseNotFoundException {
        Exercise exercise = exerciseMapper.mapToExercise(exerciseDto);
        Exercise updatedExercise = exerciseService.updateExercise(exercise);
        return ResponseEntity.ok(exerciseMapper.mapToExerciseDto(updatedExercise));
    }
}
