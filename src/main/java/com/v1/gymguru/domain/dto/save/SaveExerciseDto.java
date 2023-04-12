package com.v1.gymguru.domain.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveExerciseDto {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
}
