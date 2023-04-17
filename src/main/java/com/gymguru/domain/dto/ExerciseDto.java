package com.gymguru.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExerciseDto {
    private Long id;
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
    private Long planId;
}
