package com.v1.gymguru.adapter.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAccountDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
