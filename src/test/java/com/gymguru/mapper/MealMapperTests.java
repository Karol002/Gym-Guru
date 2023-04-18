package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.domain.*;
import com.gymguru.domain.dto.MealDto;
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
public class MealMapperTests {

    @Autowired
    private MealMapper mealMapper;

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
    void testMapToMeal() throws PlanNotFoundException, EmailAlreadyExistException, TrainerPriceInCorrectException {
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

        MealDto mealDto = new MealDto(1L, "Chicken", "Bake for 10 min", plan.getId());

        //When
        Meal result = mealMapper.mapToMeal(mealDto);

        //Then
        assertNotNull(result);
        assertEquals(mealDto.getName(), result.getName());
        assertEquals(mealDto.getCookInstruction(), result.getCookInstruction());
        assertEquals(plan.getId(), result.getPlan().getId());
    }

    @Test
    void testMapToMealDto() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
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

        Meal meal = new Meal("Chicken", "Bake for 10 min", plan);
        meal.setId(1L);

        //When
        MealDto result = mealMapper.mapToMealDto(meal);

        //Then
        assertNotNull(result);
        assertEquals(meal.getId(), result.getId());
        assertEquals(meal.getName(), result.getName());
        assertEquals(meal.getCookInstruction(), result.getCookInstruction());
        assertEquals(plan.getId(), result.getPlanId());
    }

    @Test
    void testMapToMealDtoList() throws TrainerPriceInCorrectException, EmailAlreadyExistException {
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

        Meal meal = new Meal("Chicken", "Bake for 10 min", plan);
        meal.setId(1L);


        List<Meal> meals = new ArrayList<>();
        meals.add(meal);

        //When
        List<MealDto> result = mealMapper.mapToMealDtoList(meals);

        //Then
        assertNotNull(result);
        assertEquals(meals.size(), result.size());
        assertEquals(meal.getId(), result.get(0).getId());
        assertEquals(meal.getName(), result.get(0).getName());
        assertEquals(meal.getCookInstruction(), result.get(0).getCookInstruction());
        assertEquals(plan.getId(), result.get(0).getPlanId());
    }
}
