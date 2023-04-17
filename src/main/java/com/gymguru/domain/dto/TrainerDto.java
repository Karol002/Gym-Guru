package com.gymguru.domain.dto;

import com.gymguru.domain.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TrainerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private BigDecimal monthPrice;
    private Specialization specialization;
    private Long credentialId;
}
