package com.v1.gymguru.mapper;

import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.dto.internal.exist.ExistTrainerDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertTrainerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerMapper {

    public Trainer mapToTrainer(final InsertTrainerDto insertTrainerDto)  {
        return new Trainer(
                insertTrainerDto.getEmail(),
                insertTrainerDto.getPassword(),
                insertTrainerDto.getFirstName(),
                insertTrainerDto.getLastName(),
                insertTrainerDto.getDescription(),
                insertTrainerDto.getEducation()
        );
    }

    public Trainer mapToTrainer(final ExistTrainerDto existTrainerDto) {
        return new Trainer(
                existTrainerDto.getId(),
                existTrainerDto.getEmail(),
                existTrainerDto.getPassword(),
                existTrainerDto.getFirstName(),
                existTrainerDto.getLastName(),
                existTrainerDto.getDescription(),
                existTrainerDto.getEducation()
        );
    }

    public ExistTrainerDto mapToExistTrainerDto(final Trainer trainer) {
        return new ExistTrainerDto(
                trainer.getId(),
                trainer.getEmail(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getDescription(),
                trainer.getEducation()
        );
    }

    public List<ExistTrainerDto> mapToExistTrainerDtoList(final List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToExistTrainerDto)
                .toList();
    }
}
