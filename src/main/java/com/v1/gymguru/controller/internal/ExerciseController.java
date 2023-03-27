package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Exercise;
import com.v1.gymguru.domain.dto.internal.exist.ExistExerciseDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertExerciseDto;
import com.v1.gymguru.mapper.ExerciseMapper;
import com.v1.gymguru.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    public ResponseEntity<List<ExistExerciseDto>> getExercisesByPlanId(@PathVariable Long planId) {
        List<Exercise> exercises = exerciseService.getExercisesByPlanId(planId);
        return ResponseEntity.ok(exerciseMapper.mapToExistExerciseDtoList(exercises));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addExercise(@RequestBody InsertExerciseDto insertExerciseDto) throws PlanNotFoundException {
        Exercise exercise = exerciseMapper.mapToExercise(insertExerciseDto);
        exerciseService.saveExercise(exercise);
        return ResponseEntity.ok().build();
    }
}
