package com.gymguru.service;

import com.gymguru.controller.exception.single.ExerciseNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.repository.ExerciseRepository;
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
