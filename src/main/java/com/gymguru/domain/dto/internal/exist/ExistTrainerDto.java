package com.gymguru.domain.dto.internal.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistTrainerDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
}
