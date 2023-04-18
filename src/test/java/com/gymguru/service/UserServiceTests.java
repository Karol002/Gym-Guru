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
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

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
    void testGetUsersEmails() throws EmailAlreadyExistException, UserNotFoundException {
        // Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "johnson", credential2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        // When
        List<User> users = userService.getAllUsers();

        //Then
        assertEquals(2, users.size());
    }

    @Test
    void testGetAllEmails() throws EmailAlreadyExistException {
        // Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "jphnson", credential2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        //When
        List<String> emails = userService.getAllEmails();

        // Then
        assertEquals(2, emails.size());
        assertTrue(emails.contains("test1@example.com"));
        assertTrue(emails.contains("test2@example.com"));
    }

    @Test
    void testGetUserById() throws EmailAlreadyExistException, UserNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        User resultUser = userService.getUserById(user.getId());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    void testGetUserByEmail() throws EmailAlreadyExistException, CredentialNotFoundException, UserNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        User resultUser = userService.getUserByEmail(credential.getEmail());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    void testSaveUser() throws EmailAlreadyExistException, UserNotFoundException, CredentialNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        //When
        userService.saveUser(user);
        User resultUser = userService.getUserByEmail(credential.getEmail());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);
        userService.saveUser(user);

        //When
        user.setFirstName("michael");
        userService.updateUser(user);

        //Then
        assertEquals("michael", userService.getUserById(user.getId()).getFirstName());
    }

    @Test
    void testSaveUserWithExistingEmail() throws EmailAlreadyExistException {
        //Given
        Credential credential1 = new Credential("test@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "johnson", credential2);

        userService.saveUser(user1);

        //Then
        assertThrows(EmailAlreadyExistException.class, () -> userService.saveUser(user2));
    }

    @Test
    void testCascadeDeleteCredential() throws EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        int sizeBeforeDelete = userService.getAllUsers().size();
        assertThrows(DataIntegrityViolationException.class, () -> deleter.deleteFromCredentials());
        int sizeAfterDelete = userService.getAllUsers().size();

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
        int sizeBeforeDelete = userService.getAllUsers().size();
        deleter.deleteFromSubscriptions();
        int sizeAfterDelete = userService.getAllUsers().size();

        //Then
        assertEquals(2, sizeBeforeDelete);
        assertEquals(2, sizeAfterDelete);
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
        int sizeBeforeDelete = userService.getAllUsers().size();
        deleter.deleteFromSubscriptions();
        int sizeAfterDelete = userService.getAllUsers().size();

        //Then
        assertEquals(2, sizeBeforeDelete);
        assertEquals(2, sizeAfterDelete);
    }
}
