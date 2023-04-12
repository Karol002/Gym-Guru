package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.dto.CredentialDto;
import com.v1.gymguru.domain.dto.save.SaveCredentialDto;

import java.util.List;

public class CredentialMapper {

    public Credential mapToMeal(final CredentialDto credentialDto) throws PlanNotFoundException {
        return new Credential(
                credentialDto.getId(),
                credentialDto.getEmail(),
                credentialDto.getPassword(),
                credentialDto.getCredentialType()
        );
    }

    public CredentialDto mapToCredentialDto(final Credential credential) {
        return new CredentialDto(
                credential.getId(),
                credential.getEmail(),
                credential.getPassword(),
                credential.getRole()
        );
    }

    public List<CredentialDto> mapToCredentialDtoList(List<Credential> credentials) {
        return credentials.stream()
                .map(this::mapToCredentialDto)
                .toList();
    }
}
