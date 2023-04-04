package com.v1.gymguru.adapter;

import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.CredentialType;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.User;
import lombok.RequiredArgsConstructor;
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
                toCredential(trainerAccountDto)
        );
    }

    public Credential toCredential(final TrainerAccountDto account) {
        return new Credential(
                account.getEmail(),
                account.getPassword(),
                CredentialType.ROLE_TRAINER
        );
    }

    public Credential toCredential(final UserAccountDto account) {
        return new Credential(
                account.getEmail(),
                account.getPassword(),
                CredentialType.ROLE_USER
        );
    }

    public User toUser(final UserAccountDto trainerAccountDto) {
        return new User(
                trainerAccountDto.getFirstName(),
                trainerAccountDto.getLastName(),
                toCredential(trainerAccountDto)
        );
    }
}
