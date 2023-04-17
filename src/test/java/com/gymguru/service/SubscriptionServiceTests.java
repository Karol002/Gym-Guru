package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.domain.Credential;
import com.gymguru.domain.Subscription;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SubscriptionServiceTests {

    @Autowired
    private SubscriptionService subscriptionService;

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
    void testGetAllSubscriptions() {
        Credential userCredential = new Credential("user@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user2 = new User("Mike", "Johnson", userCredential);
        Trainer trainer1 = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("55"), Specialization.Health, trainerCredential);

        Subscription subscription = new Subscription()

    }
}
