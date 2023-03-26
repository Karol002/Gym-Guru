package com.gymguru.domain.dto.exist;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistExerciseDto {
    private Long id;
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
    private Long planId;
}
