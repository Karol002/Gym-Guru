package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.domain.*;
import com.gymguru.domain.dto.ExerciseDto;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import com.gymguru.service.PlanService;
import com.gymguru.service.TrainerService;
import com.gymguru.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExerciseMapperTests {
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testMapToExercise() throws PlanNotFoundException, EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user, trainer);

        userService.saveUser(user);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        ExerciseDto exerciseDto = new ExerciseDto(plan.getId(), "Push-ups", "Do push-ups every day", 3, 10, plan.getId());

        //When
        Exercise result = exerciseMapper.mapToExercise(exerciseDto);

        //Then
        assertNotNull(result);
        assertEquals(exerciseDto.getName(), result.getName());
        assertEquals(exerciseDto.getDescription(), result.getDescription());
        assertEquals(exerciseDto.getSeriesQuantity(), result.getSeriesQuantity());
        assertEquals(exerciseDto.getRepetitionsQuantity(), result.getRepetitionsQuantity());
        assertEquals(plan.getId(), result.getPlan().getId());
    }

    @Test
    void testMapToExerciseDto() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user, trainer);

        userService.saveUser(user);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        Exercise exercise = new Exercise("Push-ups", "Do push-ups every day", 3, 10, plan);
        exercise.setId(1L);

        //When
        ExerciseDto result = exerciseMapper.mapToExerciseDto(exercise);

        //Then
        assertNotNull(result);
        assertEquals(exercise.getId(), result.getId());
        assertEquals(exercise.getName(), result.getName());
        assertEquals(exercise.getDescription(), result.getDescription());
        assertEquals(exercise.getSeriesQuantity(), result.getSeriesQuantity());
        assertEquals(exercise.getRepetitionsQuantity(), result.getRepetitionsQuantity());
        assertEquals(plan.getId(), result.getPlanId());
    }

    @Test
    void testMapToExerciseDtoList() throws TrainerPriceInCorrectException, EmailAlreadyExistException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user, trainer);

        userService.saveUser(user);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        Exercise exercise1 = new Exercise("Push-ups", "Do push-ups every day", 3, 10, plan);
        exercise1.setId(1L);
        Exercise exercise2 = new Exercise("Squats", "Do squats every day", 3, 10, plan);
        exercise2.setId(2L);

        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise1);
        exercises.add(exercise2);

        //When
        List<ExerciseDto> result = exerciseMapper.mapToExerciseDtoList(exercises);

        //Then
        assertNotNull(result);
        assertEquals(exercises.size(), result.size());
        assertEquals(exercise1.getId(), result.get(0).getId());
        assertEquals(exercise1.getName(), result.get(0).getName());
        assertEquals(exercise1.getDescription(), result.get(0).getDescription());
        assertEquals(exercise1.getSeriesQuantity(), result.get(0).getSeriesQuantity());
        assertEquals(exercise1.getRepetitionsQuantity(), result.get(0).getRepetitionsQuantity());
        assertEquals(plan.getId(), result.get(0).getPlanId());
        assertEquals(exercise2.getId(), result.get(1).getId());
        assertEquals(exercise2.getName(), result.get(1).getName());
        assertEquals(exercise2.getDescription(), result.get(1).getDescription());
        assertEquals(exercise2.getSeriesQuantity(), result.get(1).getSeriesQuantity());
        assertEquals(exercise2.getRepetitionsQuantity(), result.get(1).getRepetitionsQuantity());
        assertEquals(plan.getId(), result.get(1).getPlanId());
    }
}