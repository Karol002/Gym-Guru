package com.gymguru.controller.internal;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.domain.dto.internal.exist.ExistExerciseDto;
import com.gymguru.domain.dto.internal.insert.InsertExerciseDto;
import com.gymguru.mapper.ExerciseMapper;
import com.gymguru.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/exercise")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @GetMapping
    public ResponseEntity<List<ExistExerciseDto>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exerciseMapper.mapToExistExerciseDtoList(exercises));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addExercise(@RequestBody InsertExerciseDto insertExerciseDto) throws PlanNotFoundException {
        Exercise exercise = exerciseMapper.mapToExercise(insertExerciseDto);
        exerciseService.saveExercise(exercise);
        return ResponseEntity.ok().build();
    }
}
