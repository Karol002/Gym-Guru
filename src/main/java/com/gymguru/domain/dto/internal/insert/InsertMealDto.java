package com.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class InsertMealDto {
    private String name;
    private String cookInstruction;
    private Long planId;
}
