package com.v1.gymguru.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanDto {
    private Long id;
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
}
