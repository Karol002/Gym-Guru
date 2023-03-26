package com.gymguru.service;

import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.domain.Trainer;
import com.gymguru.repository.TrainerRepository;
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
