package com.v1.gymguru.domain.dto.internal.exist;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExistSubscriptionDto {
    private Long id;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private Long trainerId;
}
