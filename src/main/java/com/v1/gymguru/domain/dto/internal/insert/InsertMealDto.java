package com.v1.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertMealDto {
    private String name;
    private String cookInstruction;
    private Long planId;
}
