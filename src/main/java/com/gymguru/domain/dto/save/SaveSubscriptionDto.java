package com.gymguru.domain.dto.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SaveSubscriptionDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
    private Long trainerId;
}
