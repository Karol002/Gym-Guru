package com.gymguru.domain.dto.insert;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InsertSubscriptionDto {
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private Long trainerId;
}
