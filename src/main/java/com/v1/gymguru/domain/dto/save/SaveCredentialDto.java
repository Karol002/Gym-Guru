package com.v1.gymguru.domain.dto.save;

import com.v1.gymguru.domain.CredentialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveCredentialDto {
    private String email;
    private String password;
    private CredentialType credentialType;
}
