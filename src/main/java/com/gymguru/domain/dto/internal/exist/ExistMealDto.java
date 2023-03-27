package com.gymguru.domain.dto.internal.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistMealDto {
    private Long id;
    private String name;
    private String cookInstruction;
    private Long planId;
}
