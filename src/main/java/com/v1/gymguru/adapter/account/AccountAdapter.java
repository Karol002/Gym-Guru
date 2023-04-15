package com.v1.gymguru.adapter.account;

import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.enums.CredentialType;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAdapter {
    private final PasswordEncoder passwordEncoder;

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

    public Credential toCredential(final TrainerAccountDto account) {
        return new Credential(
                account.getEmail(),
                passwordEncoder.encode(account.getPassword()),
                CredentialType.ROLE_TRAINER
        );
    }

    public Credential toCredential(final UserAccountDto account) {
        return new Credential(
                account.getEmail(),
                passwordEncoder.encode(account.getPassword()),
                CredentialType.ROLE_USER
        );
    }

    public User toUser(final UserAccountDto userAccountDto) {
        return new User(
                userAccountDto.getFirstName(),
                userAccountDto.getLastName(),
                toCredential(userAccountDto)
        );
    }
}
