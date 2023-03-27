package com.gymguru.domain.dto.internal.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertTrainerDto {
    private String description;
    private String education;
    private Long personId;
}
