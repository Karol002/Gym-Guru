package com.v1.gymguru.domain.dto.internal.exist;

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

    public ExistTrainerDto(Long id, String email, String firstName, String lastName, String description, String education) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
    }
}
