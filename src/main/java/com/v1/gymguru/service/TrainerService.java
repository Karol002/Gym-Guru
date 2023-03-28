package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public  Trainer getTrainerById(Long id) throws TrainerNotFoundException {
        return trainerRepository.findById(id).orElseThrow(TrainerNotFoundException::new);
    }

    public Optional<Trainer> getTrainerByEmail(String email) {
        return trainerRepository.findByEmail(email);
    }

    public Trainer saveTrainer(final Trainer trainer) {
        return trainerRepository.save(trainer);
    }
}
