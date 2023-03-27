package com.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertTrainerDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
}
