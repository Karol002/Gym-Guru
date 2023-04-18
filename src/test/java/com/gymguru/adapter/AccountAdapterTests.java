package com.gymguru.adapter;

import com.gymguru.adapter.account.AccountAdapter;
import com.gymguru.adapter.account.TrainerAccountDto;
import com.gymguru.adapter.account.UserAccountDto;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class AccountAdapterTests {

    @Autowired
    AccountAdapter accountAdapter;

    @Test
    void toTrainer_ShouldReturnTrainer_WithCorrectFields() {
        //Given
        TrainerAccountDto trainerDto = new TrainerAccountDto(
                "John",
                "Doe",
                "Fitness trainer",
                "BSc in Sports Science",
                "john.doe@example.com",
                "password",
                BigDecimal.valueOf(99.99),
                Specialization.Strength
        );

        //When
        Trainer trainer = accountAdapter.toTrainer(trainerDto);

        //Then
        assertEquals("John", trainer.getFirstName());
        assertEquals("Doe", trainer.getLastName());
        assertEquals("Fitness trainer", trainer.getDescription());
        assertEquals("BSc in Sports Science", trainer.getEducation());
        assertEquals(BigDecimal.valueOf(99.99), trainer.getMonthPrice());
        assertEquals(Specialization.Strength, trainer.getSpecialization());
        assertNotNull(trainer.getCredential());
        assertEquals("john.doe@example.com", trainer.getCredential().getEmail());
        assertEquals("password", trainer.getCredential().getPassword());
        assertEquals(CredentialType.ROLE_TRAINER, trainer.getCredential().getRole());
    }

    @Test
    void toUser_ShouldReturnUser_WithCorrectFields() {
        //Given
        UserAccountDto userDto = new UserAccountDto(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "password"
        );
        AccountAdapter adapter = new AccountAdapter();

        // when
        User user = adapter.toUser(userDto);

        // then
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertNotNull(user.getCredential());
        assertEquals("jane.doe@example.com", user.getCredential().getEmail());
        assertEquals("password", user.getCredential().getPassword());
        assertEquals(CredentialType.ROLE_USER, user.getCredential().getRole());
    }

}
