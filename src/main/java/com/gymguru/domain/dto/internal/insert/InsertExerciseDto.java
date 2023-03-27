package com.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class InsertExerciseDto {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
    private Long planId;
}
