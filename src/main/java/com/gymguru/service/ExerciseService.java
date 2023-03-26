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
