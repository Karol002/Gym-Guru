package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.Specialization;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final CredentialService credentialService;

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public List<Trainer> getAllBySpecialization(Specialization specialization) {
        return trainerRepository.findAllBySpecialization(specialization);
    }

    public  Trainer getTrainerById(Long id) throws TrainerNotFoundException {
        return trainerRepository.findById(id).orElseThrow(TrainerNotFoundException::new);
    }

    public Trainer getTrainerByEmail(String email) throws CredentialNotFoundException, UserNotFoundException, TrainerNotFoundException {
        Long credentialId = credentialService.getCredentialIdByEmail(email);
        return trainerRepository.findByCredentialId(credentialId).orElseThrow(TrainerNotFoundException::new);
    }

    @Transactional
    public void saveTrainer(final Trainer trainer, Credential credential) throws EmailAlreadyExistException {
        if (credentialService.isEmailAvailable(credential.getEmail())) {
            trainerRepository.save(trainer);
        } else  throw new EmailAlreadyExistException();
    }

    public Trainer updateTrainer(final Trainer trainer) throws TrainerNotFoundException {
        if (trainerRepository.existsById(trainer.getId())) {
            return trainerRepository.save(trainer);
        } else throw new TrainerNotFoundException();
    }
}
