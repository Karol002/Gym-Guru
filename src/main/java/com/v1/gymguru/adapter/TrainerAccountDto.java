package com.v1.gymguru.adapter;

import com.v1.gymguru.domain.CredentialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainerAccountDto {
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private String email;
    private String password;
}
