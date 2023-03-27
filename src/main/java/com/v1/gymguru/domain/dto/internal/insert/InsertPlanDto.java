package com.v1.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertPlanDto {
    private String description;
    private Long userId;
}
