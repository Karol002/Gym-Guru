package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.Plan;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.PlanDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PlanMapperTests {

    @Autowired
    private PlanMapper planMapper;

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
    void testMapToPlan() throws UserNotFoundException, TrainerNotFoundException, EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("90"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user, trainer);

        userService.saveUser(user);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        PlanDto planDto = new PlanDto(plan.getId(), "Eat healthy food", "Jogging in the park", user.getId(), trainer.getId());

        //When
        Plan result = planMapper.mapToPlan(planDto);

        //Then
        assertNotNull(result);
        assertEquals(planDto.getDietDescription(), result.getDietDescription());
        assertEquals(planDto.getExerciseDescription(), result.getExerciseDescription());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(trainer.getId(), result.getTrainer().getId());
    }

    @Test
    void testMapToPlanDto() {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("Eat healthy food", "Jogging in the park", user, trainer);

        //When
        PlanDto result = planMapper.mapToPlanDto(plan);

        //Then
        assertNotNull(result);
        assertEquals(plan.getId(), result.getId());
        assertEquals(plan.getDietDescription(), result.getDietDescription());
        assertEquals(plan.getExerciseDescription(), result.getExerciseDescription());
        assertEquals(plan.getUser().getId(), result.getUserId());
        assertEquals(plan.getTrainer().getId(), result.getTrainerId());
    }
}
