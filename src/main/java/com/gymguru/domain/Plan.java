package com.gymguru.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PLANS")
public class Plan {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "DIET_DESCRIPTION", length = 500)
    private String dietDescription;

    @NotNull
    @Column(name = "TRAINING_DESCRIPTION", length = 500)
    private String exerciseDescription;

    @NotNull
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    @OneToMany(targetEntity = Exercise.class,
            mappedBy = "plan",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(targetEntity = Meal.class,
            mappedBy = "plan",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Meal> meals = new ArrayList<>();

    public Plan(String dietDescription, String exerciseDescription, User user, Trainer trainer) {
        this.dietDescription = dietDescription;
        this.exerciseDescription = exerciseDescription;
        this.user = user;
        this.trainer = trainer;
    }

    public Plan(Long id, String dietDescription, String exerciseDescription, User user, Trainer trainer) {
        this.id = id;
        this.dietDescription = dietDescription;
        this.exerciseDescription = exerciseDescription;
        this.user = user;
        this.trainer = trainer;
    }
}
