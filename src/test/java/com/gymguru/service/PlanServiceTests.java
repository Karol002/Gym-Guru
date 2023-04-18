package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.PlanForUserIdNotFoundException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
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
public class PlanServiceTests {

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
    void testFindAllPlansByTrainerId() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainer1Credential = new Credential("trainer1@example.com", "password3", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);

        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainer1Credential);


        Plan plan1 = new Plan("test1 diet description", "test1 training description", user1, trainer1);
        Plan plan2 = new Plan("test2 diet description", "test2 training description", user2, trainer1);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer1);
        planService.savePlan(plan1);
        planService.savePlan(plan2);

        //When
        List<Plan> plans = planService.getAllPlansByTrainerId(trainer1.getId());

        //Then
        assertEquals(2, plans.size());
    }

    @Test
    void testGetPlanByUserId() throws EmailAlreadyExistException, TrainerPriceInCorrectException, PlanForUserIdNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan1 = new Plan("test1 diet description", "test1 training description", user1, trainer);
        Plan plan2 = new Plan("test2 diet description", "test2 training description", user2, trainer);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan1);
        planService.savePlan(plan2);

        //When
        Plan resultPlan = planService.getPlanByUserId(user1.getId());
        Long userId = user1.getId();

        //Then
        assertEquals(userId, resultPlan.getUser().getId());
    }

    @Test
    void testGetPlanById() throws EmailAlreadyExistException, TrainerPriceInCorrectException, PlanNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan1 = new Plan("test1 diet description", "test1 training description", user1, trainer);
        Plan plan2 = new Plan("test2 diet description", "test2 training description", user2, trainer);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan1);
        planService.savePlan(plan2);

        //When
        Plan resultPlan = planService.getPlanById(plan1.getId());
        Long planId = plan1.getId();

        //Then
        assertEquals(planId, resultPlan.getId());
    }

    @Test
    void testSavePlan() throws EmailAlreadyExistException, TrainerPriceInCorrectException, PlanNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        //When
        Plan resultPlan = planService.getPlanById(plan.getId());
        Long planId = plan.getId();

        //Then
        assertEquals(planId, resultPlan.getId());
    }

    @Test
    void testUpdatePlan() throws EmailAlreadyExistException, TrainerPriceInCorrectException, PlanNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        //When
        plan.setDietDescription("Updated diet description");
        plan.setExerciseDescription("Updated exercise description");
        planService.updatePlan(plan);
        Plan resultPlan = planService.getPlanById(plan.getId());

        //Then
        assertEquals(plan.getDietDescription(), resultPlan.getDietDescription());
        assertEquals(plan.getExerciseDescription(), resultPlan.getExerciseDescription());
    }

    @Test
    void testCascadeWhenDeleteUser() throws EmailAlreadyExistException, TrainerPriceInCorrectException  {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        //When
        int sizeBeforeDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();
        deleter.deleteFromUsers();
        int sizeAfterDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeleteTrainer() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan = new Plan("test1 diet description", "test1 training description", user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan);

        //When
        int sizeBeforeDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();
        deleter.deleteFromTrainers();
        int sizeAfterDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeleteMeal() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
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
        int sizeBeforeDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();
        deleter.deleteFromMeals();
        int sizeAfterDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeleteExercise() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
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
        int sizeBeforeDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();
        deleter.deleteFromExercises();
        int sizeAfterDelete = planService.getAllPlansByTrainerId(trainer.getId()).size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }
}
