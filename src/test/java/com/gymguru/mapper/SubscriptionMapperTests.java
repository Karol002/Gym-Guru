package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.*;
import com.gymguru.domain.Credential;
import com.gymguru.domain.Subscription;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.SubscriptionDto;
import com.gymguru.domain.dto.save.SaveSubscriptionDto;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import com.gymguru.service.TrainerService;
import com.gymguru.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SubscriptionMapperTests {

    @Autowired
    private SubscriptionMapper subscriptionMapper;

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
    void testMapToSubscriptionDto() {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user, trainer);

        //When
        SubscriptionDto result = subscriptionMapper.mapToSubscriptionDto(subscription);

        //Then
        assertNotNull(result);
        assertEquals(subscription.getId(), result.getId());
        assertEquals(subscription.getPrice(), result.getPrice());
        assertEquals(subscription.getStartDate(), result.getStartDate());
        assertEquals(subscription.getEndDate(), result.getEndDate());
        assertEquals(subscription.getUser().getId(), result.getUserId());
        assertEquals(subscription.getTrainer().getId(), result.getTrainerId());
    }

    @Test
    void testMapToSubscriptionDtoList() {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);

        Subscription subscription = new Subscription(LocalDate.now(), LocalDate.now().plusMonths(3), user, trainer);

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);

        //When
        List<SubscriptionDto> resultList = subscriptionMapper.mapToSubscriptionDtoList(subscriptionList);

        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(subscription.getId(), resultList.get(0).getId());
        assertEquals(subscription.getPrice(), resultList.get(0).getPrice());
        assertEquals(subscription.getStartDate(), resultList.get(0).getStartDate());
        assertEquals(subscription.getEndDate(), resultList.get(0).getEndDate());
    }

    @Test
    void testMapToSubscription() throws EmailAlreadyExistException, TrainerPriceInCorrectException, InCorrectSubscriptionDataException, UserNotFoundException, TrainerNotFoundException {
        //Given
        Credential user1Credential = new Credential("user1@example.com", "password1", CredentialType.ROLE_USER);
        Credential trainerCredential = new Credential("trainer@example.com", "password2", CredentialType.ROLE_TRAINER);

        User user = new User("Mike", "Johnson", user1Credential);
        Trainer trainer = new Trainer("John", "Smith", "California School",
                "I am trainer for 5 years", new BigDecimal("100"), Specialization.Health, trainerCredential);


        userService.saveUser(user);
        trainerService.saveTrainer(trainer);

        SaveSubscriptionDto subscriptionDto = new SaveSubscriptionDto(LocalDate.now(),
                LocalDate.now().plusMonths(5), user.getId(), trainer.getId());

        //When
        Subscription result = subscriptionMapper.mapToSubscription(subscriptionDto);

        //Then
        assertNotNull(result);
        assertEquals(subscriptionDto.getStartDate(), result.getStartDate());
        assertEquals(subscriptionDto.getEndDate(), result.getEndDate());
        assertEquals(subscriptionDto.getUserId(), result.getUser().getId());
        assertEquals(subscriptionDto.getTrainerId(), result.getTrainer().getId());
    }

}

