package com.v1.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
