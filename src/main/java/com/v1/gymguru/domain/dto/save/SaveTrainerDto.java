package com.v1.gymguru.domain.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveTrainerDto {
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private Long credentialId;
}
