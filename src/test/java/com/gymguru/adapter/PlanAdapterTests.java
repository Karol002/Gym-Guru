package com.gymguru.adapter;

import com.gymguru.adapter.plan.CompletePlanDto;
import com.gymguru.adapter.plan.PlanAdapter;
import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.Plan;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.save.SaveExerciseDto;
import com.gymguru.domain.dto.save.SaveMealDto;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
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

@SpringBootTest
public class PlanAdapterTests {

    @Autowired
    TrainerService trainerService;

    @Autowired
    UserService userService;

    @Autowired
    PlanAdapter planAdapter;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testToPlan() throws UserNotFoundException, TrainerNotFoundException, EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential userCredential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", userCredential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);
        userService.saveUser(user);
        trainerService.saveTrainer(trainer);

        String dietDescription = "Low-carb diet plan";
        String exerciseDescription = "Full-body workout plan";

        List<SaveExerciseDto> saveExerciseDtos = new ArrayList<>();
        saveExerciseDtos.add(new SaveExerciseDto("Bench Press", "Chest exercise", 3, 8));
        saveExerciseDtos.add(new SaveExerciseDto("Deadlift", "Full body exercise", 3, 5));

        List<SaveMealDto> saveMealDtos = new ArrayList<>();
        saveMealDtos.add(new SaveMealDto("Omelette", "Cook eggs and vegetables in a pan"));
        saveMealDtos.add(new SaveMealDto("Grilled chicken with vegetables", "Grill chicken and vegetables"));

        CompletePlanDto completePlanDto = new CompletePlanDto(dietDescription, exerciseDescription, user.getId(), trainer.getId(), saveExerciseDtos, saveMealDtos);
        PlanAdapter planAdapter = new PlanAdapter(userService, trainerService);

        //When
        Plan plan = planAdapter.toPlan(completePlanDto);

        //Then
        assertEquals(dietDescription, plan.getDietDescription());
        assertEquals(exerciseDescription, plan.getExerciseDescription());
        assertEquals(user.getId(), plan.getUser().getId());
        assertEquals(trainer.getId(), plan.getTrainer().getId());
        assertEquals(2, plan.getExercises().size());
        assertEquals(2, plan.getMeals().size());
    }
}
