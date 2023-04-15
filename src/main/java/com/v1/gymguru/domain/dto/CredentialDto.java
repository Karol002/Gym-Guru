package com.v1.gymguru.domain.dto;

import com.v1.gymguru.domain.enums.CredentialType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialDto {
    private Long id;
    private String email;
    private String password;
    private CredentialType credentialType;
}
