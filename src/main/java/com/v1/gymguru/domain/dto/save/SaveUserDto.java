package com.v1.gymguru.domain.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
