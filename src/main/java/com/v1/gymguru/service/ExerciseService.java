package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.ExerciseNotFoundException;
import com.v1.gymguru.domain.Exercise;
import com.v1.gymguru.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    public List<Exercise> getExercisesByPlanId(Long planId) {
        return exerciseRepository.findAllByPlanId(planId);
    }
    public void updateExercise(final Exercise exercise) throws ExerciseNotFoundException {
        if (exerciseRepository.existsById(exercise.getId())) {
            exerciseRepository.save(exercise);
        } else throw new ExerciseNotFoundException();
    }
}
