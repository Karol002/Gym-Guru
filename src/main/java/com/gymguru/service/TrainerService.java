package com.gymguru.service;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.enums.Specialization;
import com.gymguru.domain.Trainer;
import com.gymguru.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public Trainer getTrainerByEmail(String email) throws CredentialNotFoundException, TrainerNotFoundException {
        Long credentialId = credentialService.getCredentialIdByEmail(email);
        return trainerRepository.findByCredentialId(credentialId).orElseThrow(TrainerNotFoundException::new);
    }

    @Transactional
    public void saveTrainer(Trainer trainer) throws EmailAlreadyExistException {
        if (credentialService.isEmailAvailable(trainer.getCredential().getEmail())) {
            credentialService.encodePassword(trainer.getCredential());
            trainerRepository.save(trainer);
        } else  throw new EmailAlreadyExistException();
    }

    @Transactional
    public Trainer updateTrainer(final Trainer trainer) throws TrainerNotFoundException {
        if (trainerRepository.existsById(trainer.getId())) {
            return trainerRepository.save(trainer);
        } else throw new TrainerNotFoundException();
    }
}
