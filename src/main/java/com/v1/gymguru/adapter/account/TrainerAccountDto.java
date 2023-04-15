package com.v1.gymguru.adapter.account;

import com.v1.gymguru.domain.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TrainerAccountDto {
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private String email;
    private String password;
    private BigDecimal monthPrice;
    private Specialization specialization;
}
