package com.v1.gymguru.controller;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Exercise;
import com.v1.gymguru.domain.dto.ExerciseDto;
import com.v1.gymguru.domain.dto.save.SaveExerciseDto;
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
    public ResponseEntity<List<ExerciseDto>> getExercisesByPlanId(@PathVariable Long planId) {
        List<Exercise> exercises = exerciseService.getExercisesByPlanId(planId);
        return ResponseEntity.ok(exerciseMapper.mapToExistExerciseDtoList(exercises));
    }

}
