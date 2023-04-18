package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.dto.TrainerDto;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import com.gymguru.service.TrainerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrainerMapperTests {

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void mapToTrainer_ShouldReturnTrainer() throws CredentialNotFoundException, EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Doe", "description", "education", BigDecimal.valueOf(20), Specialization.Strength, credential);
        trainerService.saveTrainer(trainer);

        TrainerDto trainerDto = new TrainerDto(trainer.getId(), "John", "Smith", "description2", "education2", BigDecimal.valueOf(20), Specialization.Health, credential.getId());

        //When
        Trainer result = trainerMapper.mapToTrainer(trainerDto);

        //Then
        assertNotNull(result);
        assertEquals(trainerDto.getId(), result.getId());
        assertEquals(trainerDto.getFirstName(), result.getFirstName());
        assertEquals(trainerDto.getLastName(), result.getLastName());
        assertEquals(trainerDto.getDescription(), result.getDescription());
        assertEquals(trainerDto.getEducation(), result.getEducation());
        assertEquals(trainerDto.getMonthPrice(), result.getMonthPrice());
        assertEquals(trainerDto.getSpecialization(), result.getSpecialization());
        assertEquals(trainerDto.getCredentialId(), result.getCredential().getId());
    }

    @Test
    void mapToTrainer_ShouldThrowCredentialNotFoundException() {
        //Given
        TrainerDto trainerDto = new TrainerDto(1L, "John", "Doe", "description", "education", BigDecimal.valueOf(35), Specialization.Health, 1L);

        //When & Then
        assertThrows(CredentialNotFoundException.class, () -> trainerMapper.mapToTrainer(trainerDto));
    }

    @Test
    void mapToTrainerDto_ShouldReturnTrainerDto() {
        //Given
        Credential credential = new Credential(1L, "test@example.com", "password", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer(1L, "John", "Doe", "description", "education", BigDecimal.valueOf(35), Specialization.Health, credential);

        //When
        TrainerDto result = trainerMapper.mapToTrainerDto(trainer);

        //Then
        assertNotNull(result);
        assertEquals(trainer.getId(), result.getId());
        assertEquals(trainer.getFirstName(), result.getFirstName());
        assertEquals(trainer.getLastName(), result.getLastName());
        assertEquals(trainer.getDescription(), result.getDescription());
        assertEquals(trainer.getEducation(), result.getEducation());
        assertEquals(trainer.getMonthPrice(), result.getMonthPrice());
        assertEquals(trainer.getSpecialization(), result.getSpecialization());
        assertEquals(trainer.getCredential().getId(), result.getCredentialId());
    }

    @Test
    void mapToTrainerDtoList_ShouldReturnTrainerDtoList() {
        //Given
        Credential credential = new Credential(1L, "test@example.com", "password", CredentialType.ROLE_TRAINER);
        Trainer trainer1 = new Trainer(1L, "John", "Doe", "description", "education", BigDecimal.valueOf(100), Specialization.Health, credential);
        Trainer trainer2 = new Trainer(2L, "Mike", "Johnson", "description2", "education2", BigDecimal.valueOf(50), Specialization.Strength, credential);
        List<Trainer> trainerList = new ArrayList<>();
        trainerList.add(trainer1);
        trainerList.add(trainer2);

        //When
        List<TrainerDto> resultList = trainerMapper.mapToTrainerDtoList(trainerList);

        //Then
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(trainer1.getId(), resultList.get(0).getId());
        assertEquals(trainer1.getFirstName(), resultList.get(0).getFirstName());
        assertEquals(trainer1.getLastName(), resultList.get(0).getLastName());
        assertEquals(trainer1.getDescription(), resultList.get(0).getDescription());
        assertEquals(trainer1.getEducation(), resultList.get(0).getEducation());
        assertEquals(trainer1.getMonthPrice(), resultList.get(0).getMonthPrice());
        assertEquals(trainer1.getSpecialization(), resultList.get(0).getSpecialization());
        assertEquals(trainer1.getCredential().getId(), resultList.get(0).getCredentialId());

        assertEquals(trainer2.getId(), resultList.get(1).getId());
        assertEquals(trainer2.getFirstName(), resultList.get(1).getFirstName());
        assertEquals(trainer2.getLastName(), resultList.get(1).getLastName());
        assertEquals(trainer2.getDescription(), resultList.get(1).getDescription());
        assertEquals(trainer2.getEducation(), resultList.get(1).getEducation());
        assertEquals(trainer2.getMonthPrice(), resultList.get(1).getMonthPrice());
        assertEquals(trainer2.getSpecialization(), resultList.get(1).getSpecialization());
        assertEquals(trainer2.getCredential().getId(), resultList.get(1).getCredentialId());
    }

    @Test
    void mapToTrainerDtoList_ShouldReturnEmptyList() {
        //Given
        List<Trainer> trainerList = new ArrayList<>();

        //When
        List<TrainerDto> resultList = trainerMapper.mapToTrainerDtoList(trainerList);

        //Then
        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());
    }
}