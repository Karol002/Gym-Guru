package com.v1.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertExerciseDto {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
    private Long planId;
}
