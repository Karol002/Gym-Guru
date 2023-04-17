package com.gymguru.adapter.account;

import com.gymguru.domain.Credential;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAdapter {
    public Trainer toTrainer(final TrainerAccountDto trainerAccountDto) {
        return new Trainer(
                trainerAccountDto.getFirstName(),
                trainerAccountDto.getLastName(),
                trainerAccountDto.getDescription(),
                trainerAccountDto.getEducation(),
                trainerAccountDto.getMonthPrice(),
                trainerAccountDto.getSpecialization(),
                toCredential(trainerAccountDto)
        );
    }

    public User toUser(final UserAccountDto userAccountDto) {
        return new User(
                userAccountDto.getFirstName(),
                userAccountDto.getLastName(),
                toCredential(userAccountDto)
        );
    }

    private Credential toCredential(final TrainerAccountDto account) {
        return new Credential(
                account.getEmail(),
                account.getPassword(),
                CredentialType.ROLE_TRAINER
        );
    }

    private Credential toCredential(final UserAccountDto account) {
        return new Credential(
                account.getEmail(),
                account.getPassword(),
                CredentialType.ROLE_USER
        );
    }
}
