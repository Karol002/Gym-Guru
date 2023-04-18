package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.MealNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.domain.*;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MealServiceTests {

    @Autowired
    MealService mealService;

    @Autowired
    PlanService planService;

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
    void testFindMealsPlanId() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainer1Credential = new Credential("trainer1@example.com", "password3", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);

        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainer1Credential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer1);

        Exercise exercise1 = new Exercise("Test exercise 1", "Test description 1", 1, 1, plan);
        Exercise exercise2 = new Exercise("Test exercise 2", "Test description 2", 2, 2, plan);
        Meal meal1 = new Meal("Test meal 1", "Test description 2", plan);
        Meal meal2 = new Meal("Test meal 2", "Test description 2", plan);

        plan.getExercises().add(exercise1);
        plan.getExercises().add(exercise2);
        plan.getMeals().add(meal1);
        plan.getMeals().add(meal2);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer1);
        planService.savePlan(plan);

        //When
        List<Meal> meals = mealService.getMealsByPlanId(plan.getId());

        //Then
        assertEquals(2, meals.size());
    }

    @Test
    void testUpdateMeal() throws EmailAlreadyExistException, TrainerPriceInCorrectException, MealNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainer1Credential = new Credential("trainer1@example.com", "password3", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);

        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainer1Credential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer1);

        Exercise exercise1 = new Exercise("Test exercise 1", "Test description 1", 1, 1, plan);
        Meal meal1 = new Meal("Test meal 1", "Test description 2", plan);

        plan.getExercises().add(exercise1);
        plan.getMeals().add(meal1);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer1);
        planService.savePlan(plan);

        //When
        meal1.setName("Updated name");
        meal1.setCookInstruction("Updated instruction");
        mealService.updateMeal(meal1);
        Meal resultMeal = mealService.getMealsByPlanId(plan.getId()).get(0);

        //Then
        assertEquals("Updated name", resultMeal.getName());
        assertEquals("Updated instruction", resultMeal.getCookInstruction());
    }

    @Test
    void testCascadeWhenDeletePlan() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainer1Credential = new Credential("trainer1@example.com", "password3", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainer1Credential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer);

        Exercise exercise1 = new Exercise("Test exercise 1", "Test description 1", 1, 1, plan);
        Meal meal1 = new Meal("Test meal 1", "Test description 2", plan);

        plan.getExercises().add(exercise1);
        plan.getMeals().add(meal1);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        //When
        int sizeBeforeDelete = mealService.getMealsByPlanId(plan.getId()).size();
        deleter.deleteFromPlans();
        int sizeAfterDelete = mealService.getMealsByPlanId(plan.getId()).size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }
}
