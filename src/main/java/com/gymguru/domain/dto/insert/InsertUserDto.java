package com.gymguru.domain.dto.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertUserDto {
    private Long personId;
    private Long planId;
}
