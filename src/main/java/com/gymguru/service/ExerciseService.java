package com.gymguru.service;

import com.gymguru.controller.exception.single.ExerciseNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public List<Exercise> getExercisesByPlanId(Long planId) {
        return exerciseRepository.findAllByPlanId(planId);
    }

    public Exercise updateExercise(final Exercise exercise) throws ExerciseNotFoundException {
        if (exerciseRepository.existsById(exercise.getId())) {
            return exerciseRepository.save(exercise);
        } else throw new ExerciseNotFoundException();
    }
}
