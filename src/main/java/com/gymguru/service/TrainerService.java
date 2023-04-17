package com.gymguru.service;

import com.gymguru.controller.exception.single.*;
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
    public void saveTrainer(Trainer trainer) throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        if (credentialService.isEmailAvailable(trainer.getCredential().getEmail()) && isTrainerPriceCorrect(trainer)) {
            credentialService.encodePassword(trainer.getCredential());
            trainerRepository.save(trainer);
        } else  throw new EmailAlreadyExistException();
    }

    @Transactional
    public Trainer updateTrainer(final Trainer trainer) throws TrainerNotFoundException, TrainerPriceInCorrectException {
        if (trainerRepository.existsById(trainer.getId()) && isTrainerPriceCorrect(trainer)) {
            return trainerRepository.save(trainer);
        } else throw new TrainerNotFoundException();
    }

    public boolean isTrainerPriceCorrect(Trainer trainer) throws TrainerPriceInCorrectException {
        if (trainer.getMonthPrice().doubleValue() >= 20
                && trainer.getMonthPrice().doubleValue() <= 100) return true;
        else throw new TrainerPriceInCorrectException();
    }
}
