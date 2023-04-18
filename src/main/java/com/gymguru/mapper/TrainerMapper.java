package com.gymguru.mapper;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.dto.TrainerDto;
import com.gymguru.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerMapper {
    private final CredentialService credentialService;

    public Trainer mapToTrainer(final TrainerDto trainerDto) throws CredentialNotFoundException {
        return new Trainer(
                trainerDto.getId(),
                trainerDto.getFirstName(),
                trainerDto.getLastName(),
                trainerDto.getDescription(),
                trainerDto.getEducation(),
                trainerDto.getMonthPrice(),
                trainerDto.getSpecialization(),
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
                trainer.getMonthPrice(),
                trainer.getSpecialization(),
                trainer.getCredential().getId()
        );
    }

    public List<TrainerDto> mapToTrainerDtoList(final List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToTrainerDto)
                .toList();
    }
}
