package com.v1.gymguru.mapper;

import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.dto.TrainerDto;
import com.v1.gymguru.domain.dto.save.SaveTrainerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerMapper {

    public Trainer mapToTrainer(final SaveTrainerDto saveTrainerDto)  {
        return new Trainer(
                saveTrainerDto.getEmail(),
                saveTrainerDto.getPassword(),
                saveTrainerDto.getFirstName(),
                saveTrainerDto.getLastName(),
                saveTrainerDto.getDescription(),
                saveTrainerDto.getEducation()
        );
    }

    public Trainer mapToTrainer(final TrainerDto trainerDto) {
        return new Trainer(
                trainerDto.getId(),
                trainerDto.getEmail(),
                trainerDto.getPassword(),
                trainerDto.getFirstName(),
                trainerDto.getLastName(),
                trainerDto.getDescription(),
                trainerDto.getEducation()
        );
    }

    public TrainerDto mapToExistTrainerDto(final Trainer trainer) {
        return new TrainerDto(
                trainer.getId(),
                trainer.getEmail(),
                "PASSWORD IS HIDDEN",
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getDescription(),
                trainer.getEducation()
        );
    }

    public List<TrainerDto> mapToExistTrainerDtoList(final List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToExistTrainerDto)
                .toList();
    }
}
