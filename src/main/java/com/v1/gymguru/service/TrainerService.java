package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public  Trainer getTrainer(Long id) throws TrainerNotFoundException {
        return trainerRepository.findById(id).orElseThrow(TrainerNotFoundException::new);
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    public Trainer saveTrainer(final Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
