package com.gymguru.domain.dto.internal.exist;

import com.gymguru.domain.Person;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

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
