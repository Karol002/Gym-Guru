package com.gymguru.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EXERCISES")
public class Exercise {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @NotNull
    @Column(name = "SERIES_QUANTITY")
    private int seriesQuantity;

    @NotNull
    @Column(name = "REPETITIONS_QUANTITY")
    private int repetitionsQuantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PLAN_ID")
    private Plan plan;

    public Exercise(String name, String description, int seriesQuantity, int repetitionsQuantity, Plan plan) {
        this.name = name;
        this.description = description;
        this.seriesQuantity = seriesQuantity;
        this.repetitionsQuantity = repetitionsQuantity;
        this.plan = plan;
    }
}
