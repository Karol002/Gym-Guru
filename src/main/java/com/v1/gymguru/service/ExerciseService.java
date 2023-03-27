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

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise getExercise(Long id) throws ExerciseNotFoundException {
        return exerciseRepository.findById(id).orElseThrow(ExerciseNotFoundException::new);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    public Exercise saveExercise(final Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}
