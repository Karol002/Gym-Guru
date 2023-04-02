package com.v1.gymguru.domain.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SavePlanDto {
    private String description;
    private Long userId;
}
