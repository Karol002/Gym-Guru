package com.v1.gymguru.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "PLAN_DESCRIPTION")
    private String description;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    @OneToMany(targetEntity = Exercise.class,
               mappedBy = "plan",
               cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY
    )
    private List<Exercise> exercises;

    @OneToMany(targetEntity = Meal.class,
            mappedBy = "plan",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Meal> meals;

    public Plan(String description, User user, Trainer trainer) {
        this.description = description;
        this.user = user;
        this.trainer = trainer;
    }

    public Plan(Long id, String description, User user, Trainer trainer) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.trainer = trainer;
    }
}
