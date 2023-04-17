package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.InCorrectSubscriptionDataException;
import com.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.gymguru.controller.exception.single.TrainerPriceInCorrectException;
import com.gymguru.domain.*;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubscriptionServiceTests {

    @Autowired
    private SubscriptionService subscriptionService;

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
    void testGetAllSubscriptions() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
        List<Subscription> allSubscriptions = subscriptionService.getAllSubscriptions();

        //Then
        assertEquals(2, allSubscriptions.size());
    }

    @Test
    void testGetAllSubscriptionsByTrainerId() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
        List<Subscription> allSubscriptions = subscriptionService.getSubscriptionsByTrainerId(trainer.getId());

        //Then
        assertEquals(2, allSubscriptions.size());
    }

    @Test
    void testSubscriptionsWithoutPlanByTrainerId() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
        Plan plan = new Plan("test diet description", "test training description", user1, trainer);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer);
        subscriptionService.createSubscription(subscription1);
        subscriptionService.createSubscription(subscription2);
        planService.savePlan(plan);

        //When
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsWithoutPlanByTrainerId(trainer.getId());

        //Then
        assertEquals(1, subscriptions.size());
    }

    @Test
    void testSubscriptionsWithPlanByTrainerId() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainer1Credential = new Credential("trainer1@example.com", "password2", CredentialType.ROLE_TRAINER);
        Credential trainer2Credential = new Credential("trainer2@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainer1Credential);
        Trainer trainer2 = new Trainer("Mike", "Chris", "California School",
                "I am trainer for 15 years", new BigDecimal("100"), Specialization.Health, trainer2Credential);

        Subscription subscription1 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer1);
        Subscription subscription2 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(5), user2, trainer1);
        Plan plan1 = new Plan("test diet description", "test training description", user1, trainer1);
        Plan plan2 = new Plan("test diet description", "test training description", user2, trainer1);

        userService.saveUser(user1);
        userService.saveUser(user2);
        trainerService.saveTrainer(trainer1);
        trainerService.saveTrainer(trainer2);
        subscriptionService.createSubscription(subscription1);
        subscriptionService.createSubscription(subscription2);
        planService.savePlan(plan1);
        planService.savePlan(plan2);

        //When
        List<Subscription> firstSubscriptions = subscriptionService.getSubscriptionsWithPlanByTrainerId(trainer1.getId());
        List<Subscription> secondSubscriptions = subscriptionService.getSubscriptionsWithPlanByTrainerId(trainer2.getId());

        //Then
        assertEquals(2, firstSubscriptions.size());
        assertEquals(0, secondSubscriptions.size());
    }

    @Test
    void testSubcriptionByUserId() throws EmailAlreadyExistException, SubscriptionNotFoundException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
        Subscription resultSubscription = subscriptionService.getSubscriptionByUserId(user1.getId());
        Long subscriptionId = subscription1.getId();

        //Then
        assertEquals(subscriptionId, resultSubscription.getId());
    }

    @Test
    void testCreateSubscription() throws EmailAlreadyExistException, SubscriptionNotFoundException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
        Subscription resultSubscription = subscriptionService.getSubscriptionByUserId(user1.getId());
        Long subscriptionId = subscription1.getId();

        //Then
        assertEquals(subscriptionId, resultSubscription.getId());
    }

    @Test
    void testCheckSubscriptionData() {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription1 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer);
        Subscription subscription2 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(7), user2, trainer);

        //When
        boolean correctData = subscriptionService.checkSubscriptionData(subscription1);
        boolean inCorrectData = subscriptionService.checkSubscriptionData(subscription2);

        //Then
        assertTrue(correctData);
        assertFalse(inCorrectData);
    }

    @Test
    void testCalculatePrice() {
        //Given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(5);
        BigDecimal price = new BigDecimal("100");

        //When
        BigDecimal subscriptionPrice = subscriptionService.calculatePrice(startDate, endDate, price);

        //Then
        assertEquals(new BigDecimal("500"), subscriptionPrice);
    }

    @Test
    void checkIsCorrectLong() {
        //Given
        LocalDate startDate = LocalDate.now();
        LocalDate correctEndDate = LocalDate.now().plusMonths(5);
        LocalDate inCorrectEndDate = LocalDate.now().plusMonths(15);

        //When
        boolean correctResult = subscriptionService.checkIsCorrectLong(startDate, correctEndDate);
        boolean inCorrectResult = subscriptionService.checkIsCorrectLong(startDate, inCorrectEndDate);


        //Then
        assertTrue(correctResult);
        assertFalse(inCorrectResult);
    }

    @Test
    void testExtendSubscription() throws EmailAlreadyExistException, SubscriptionNotFoundException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        subscriptionService.createSubscription(subscription);

        //When
        LocalDate dateBeforeExtend = subscription.getEndDate();
        subscriptionService.extendSubscription(user1.getId(), 1L);
        LocalDate dateAfterExtend = subscriptionService.getSubscriptionByUserId(user1.getId()).getEndDate();

        //Then
        assertEquals(dateBeforeExtend.plusMonths(1), dateAfterExtend);
    }

    @Test
    void testDeleteSubscription() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
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
         int subcriptionSizeBeforeDelete = subscriptionService.getAllSubscriptions().size();
         subscriptionService.deleteSubscriptionById(subscription1.getId());
         int subscriptionSizeAfterDelete = subscriptionService.getAllSubscriptions().size();

        //Then
        assertEquals(2, subcriptionSizeBeforeDelete);
        assertEquals(1, subscriptionSizeAfterDelete);
    }

    @Test
    void testIsSubscriptionActive()  {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential user2Credential = new Credential("user2@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        User user2 = new User("Jake", "Gustavo", user2Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription1 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer);
        Subscription subscription2 = new Subscription(LocalDate.now(), LocalDate.now().minusMonths(3), user2, trainer);


        //When
        boolean activeSubscription = subscriptionService.isSubscriptionActive(subscription1);
        boolean unActiveSubscription = subscriptionService.isSubscriptionActive(subscription2);

        //Then
        assertTrue(activeSubscription);
        assertFalse(unActiveSubscription);
    }

    @Test
    void testIsSubscriptionActiveByUserId() throws EmailAlreadyExistException, InCorrectSubscriptionDataException, TrainerPriceInCorrectException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user1 = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription1 = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user1, trainer);

        userService.saveUser(user1);
        trainerService.saveTrainer(trainer);
        subscriptionService.createSubscription(subscription1);

        //When
        boolean activeSubscription = subscriptionService.isSubscriptionActive(user1.getId());

        //Then
        assertTrue(activeSubscription);
    }

    @Test
    void testCascadeWhenDeleteUser() throws EmailAlreadyExistException, TrainerPriceInCorrectException, InCorrectSubscriptionDataException {
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
        int sizeBeforeDelete = subscriptionService.getAllSubscriptions().size();
        deleter.deleteFromUsers();
        int sizeAfterDelete = subscriptionService.getAllSubscriptions().size();

        //Then
        assertEquals(2, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }

    @Test
    void testCascadeWhenDeleteTrainer() throws EmailAlreadyExistException, TrainerPriceInCorrectException, InCorrectSubscriptionDataException {
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
        int sizeBeforeDelete = subscriptionService.getAllSubscriptions().size();
        deleter.deleteFromTrainers();
        int sizeAfterDelete = subscriptionService.getAllSubscriptions().size();

        //Then
        assertEquals(2, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }
}
