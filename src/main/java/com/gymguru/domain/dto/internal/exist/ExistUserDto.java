package com.gymguru.domain.dto.internal.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExistUserDto {
    private Long id;
    private Long personId;
    private Long planId;
}
