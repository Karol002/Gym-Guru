package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.dto.TrainerDto;
import com.v1.gymguru.domain.dto.save.SaveTrainerDto;
import com.v1.gymguru.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerMapper {
    private final CredentialService credentialService;

    public Trainer mapToTrainer(final SaveTrainerDto saveTrainerDto) throws CredentialNotFoundException {
        return new Trainer(
                saveTrainerDto.getFirstName(),
                saveTrainerDto.getLastName(),
                saveTrainerDto.getDescription(),
                saveTrainerDto.getEducation(),
                credentialService.getById(saveTrainerDto.getCredentialId())
        );
    }

    public Trainer mapToTrainer(final TrainerDto trainerDto) throws CredentialNotFoundException {
        return new Trainer(
                trainerDto.getId(),
                trainerDto.getFirstName(),
                trainerDto.getLastName(),
                trainerDto.getDescription(),
                trainerDto.getEducation(),
                credentialService.getById(trainerDto.getCredentialId())
        );
    }

    public TrainerDto mapToTrainerDto(final Trainer trainer) {
        return new TrainerDto(
                trainer.getId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getDescription(),
                trainer.getEducation(),
                trainer.getCredential().getId()
        );
    }

    public List<TrainerDto> mapToExistTrainerDtoList(final List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToTrainerDto)
                .toList();
    }
}
