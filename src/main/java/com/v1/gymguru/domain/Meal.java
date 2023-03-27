package com.v1.gymguru.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meal {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COOK_INSTRUCTION")
    private String cookInstruction;

    @ManyToOne
    @JoinColumn(name = "PLAN_ID")
    private Plan plan;

    public Meal(String name, String cookInstruction, Plan plan) {
        this.name = name;
        this.cookInstruction = cookInstruction;
        this.plan = plan;
    }
}