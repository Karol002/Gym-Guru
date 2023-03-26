package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PersonNotFoundException;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.dto.exist.ExistTrainerDto;
import com.gymguru.domain.dto.insert.InsertTrainerDto;
import com.gymguru.service.PersonService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TrainerMapper {
    private final PersonService personService;

    public Trainer mapToTrainer(final InsertTrainerDto insertTrainerDto) throws PersonNotFoundException {
        return new Trainer(
                insertTrainerDto.getDescription(),
                insertTrainerDto.getEducation(),
                personService.getPerson(insertTrainerDto.getPersonId())
        );
    }

    public Trainer mapToTrainer(final ExistTrainerDto existTrainerDto) throws PersonNotFoundException {
        return new Trainer(
                existTrainerDto.getId(),
                existTrainerDto.getDescription(),
                existTrainerDto.getEducation(),
                personService.getPerson(existTrainerDto.getPersonId())
        );
    }

    public ExistTrainerDto mapToExistTrainerDto(final Trainer trainer) {
        return new ExistTrainerDto(
                trainer.getId(),
                trainer.getDescription(),
                trainer.getEducation(),
                trainer.getPerson().getId()
        );
    }

    public List<ExistTrainerDto> mapToTrainerDtoList(final List<Trainer> trainers) {
        return trainers.stream()
                .map(this::mapToExistTrainerDto)
                .toList();
    }
}
