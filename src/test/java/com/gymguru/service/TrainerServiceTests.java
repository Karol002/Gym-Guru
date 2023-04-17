package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.*;
import com.gymguru.domain.*;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrainerServiceTests {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private PlanService planService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testGetAllTrainers() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_TRAINER);
        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential1);
        Trainer trainer2 = new Trainer("Mike", "Johnson", "Miami school",
                "I am trainer for 10 years", new BigDecimal("80"), Specialization.Health, credential2);


        trainerService.saveTrainer(trainer1);
        trainerService.saveTrainer(trainer2);

        //When
        List<Trainer> trainers = trainerService.getAllTrainers();

        //Then
        assertEquals(2, trainers.size());
    }

    @Test
    void testGetAllBySpecialization() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_TRAINER);
        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential1);
        Trainer trainer2 = new Trainer("Mike", "Johnson", "Miami school",
                "I am trainer for 10 years", new BigDecimal("80"), Specialization.Strength, credential2);


        trainerService.saveTrainer(trainer1);
        trainerService.saveTrainer(trainer2);

        //When
        List<Trainer> healthTrainers = trainerService.getAllBySpecialization(Specialization.Health);
        List<Trainer> strengthTrainers = trainerService.getAllBySpecialization(Specialization.Strength);

        //Then
        assertEquals(1, healthTrainers.size());
        assertEquals(healthTrainers.get(0).getSpecialization(), Specialization.Health);

        assertEquals(1, strengthTrainers.size());
        assertEquals(strengthTrainers.get(0).getSpecialization(), Specialization.Strength);
    }

    @Test
    void testGetTrainerById() throws EmailAlreadyExistException, TrainerNotFoundException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential);

        trainerService.saveTrainer(trainer);

        //When
        Trainer resultTrainer = trainerService.getTrainerById(trainer.getId());
        Long trainerId = trainer.getId();

        //Then
        assertEquals(trainerId, resultTrainer.getId());
    }

    @Test
    void testGetTrainerByEmail() throws EmailAlreadyExistException, TrainerNotFoundException, CredentialNotFoundException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential);

        trainerService.saveTrainer(trainer);

        //When
        Trainer resultTrainer = trainerService.getTrainerByEmail(credential.getEmail());
        Long trainerId = trainer.getId();

        //Then
        assertEquals(trainerId, resultTrainer.getId());
    }

    @Test
    void testSaveTrainer() throws EmailAlreadyExistException, TrainerNotFoundException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential);

        //When
        trainerService.saveTrainer(trainer);
        Trainer resultTrainer = trainerService.getTrainerById(trainer.getId());
        Long trainerId = trainer.getId();

        //Then
        assertEquals(trainerId, resultTrainer.getId());
    }

    @Test
    void testUpdateTrainer() throws EmailAlreadyExistException, TrainerNotFoundException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential);

        trainerService.saveTrainer(trainer);

        //When
        trainer.setFirstName("michael");
        trainerService.updateTrainer(trainer);

        //Then
        assertEquals("michael", trainerService.getTrainerById(trainer.getId()).getFirstName());
    }

    @Test
    void testSaveTrainerWithExistingEmail() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Credential credential2 = new Credential("test1@example.com", "password2", CredentialType.ROLE_TRAINER);
        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential1);
        Trainer trainer2 = new Trainer("Mike", "Johnson", "Miami school",
                "I am trainer for 10 years", new BigDecimal("80"), Specialization.Strength, credential2);

        //When
        trainerService.saveTrainer(trainer1);

        //Then
        assertThrows(EmailAlreadyExistException.class, () -> trainerService.saveTrainer(trainer2));
    }

    @Test
    void testCascadeWhenDeleteCredential() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_TRAINER);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, credential);

        trainerService.saveTrainer(trainer);

        //When
        int sizeBeforeDelete = trainerService.getAllTrainers().size();
        assertThrows(DataIntegrityViolationException.class, () -> deleter.deleteFromCredentials());
        int sizeAfterDelete = trainerService.getAllTrainers().size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeleteSubscription() throws EmailAlreadyExistException, TrainerPriceInCorrectException, InCorrectSubscriptionDataException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription1 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer);
        Subscription subscription2 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(5), user2, trainer);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer);
        subscriptionService.createSubscription(subscription1);
        subscriptionService.createSubscription(subscription2);

        //When
        int sizeBeforeDelete = trainerService.getAllTrainers().size();
        deleter.deleteFromSubscriptions();
        int sizeAfterDelete = trainerService.getAllTrainers().size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeletePlan() throws EmailAlreadyExistException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Plan plan1 = new Plan("Test diet description 1", "Test plan training description 1", user1, trainer);
        Plan plan2 = new Plan("Test diet description 2", "Test plan training description 2", user2, trainer);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer);
        planService.savePlan(plan1);
        planService.savePlan(plan2);

        //When
        int sizeBeforeDelete = trainerService.getAllTrainers().size();
        deleter.deleteFromSubscriptions();
        int sizeAfterDelete = trainerService.getAllTrainers().size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }
}
