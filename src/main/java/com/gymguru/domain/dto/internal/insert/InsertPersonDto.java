package com.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertPersonDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
