package com.gymguru.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChanger {
    private String email;
    private String oldPassword;
    private String newPassword;
}
