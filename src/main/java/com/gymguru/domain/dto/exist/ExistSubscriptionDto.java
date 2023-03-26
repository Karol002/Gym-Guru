package com.gymguru.domain.dto.exist;

import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
