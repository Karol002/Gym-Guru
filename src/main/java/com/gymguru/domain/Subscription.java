package com.gymguru.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscription {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    public Subscription(BigDecimal price, LocalDate startDate, LocalDate endDate, User user, Trainer trainer) {
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.trainer = trainer;
    }
}
