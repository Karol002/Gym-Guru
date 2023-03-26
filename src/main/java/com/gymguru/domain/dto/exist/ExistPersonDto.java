package com.gymguru.domain.dto.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
public class ExistPersonDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
