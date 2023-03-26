package com.gymguru.domain.dto.exist;

import com.gymguru.domain.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@AllArgsConstructor
public class ExistMealDto {
    private Long id;
    private String name;
    private String cookInstruction;
    private Long planId;
}
