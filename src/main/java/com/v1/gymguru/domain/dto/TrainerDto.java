package com.v1.gymguru.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private Long credentialId;
}
