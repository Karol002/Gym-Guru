package com.v1.gymguru.domain.dto.internal.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistUserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public ExistUserDto(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
