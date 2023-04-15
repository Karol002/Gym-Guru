package com.v1.gymguru.domain.dto.save;

import com.v1.gymguru.domain.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SaveTrainerDto {
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private BigDecimal monthPrice;
    private Specialization specialization;
    private Long credentialId;
}
