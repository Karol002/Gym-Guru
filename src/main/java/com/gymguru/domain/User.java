package com.gymguru.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;


    @ManyToOne
    @JoinColumn(name = "PLAN_ID")
    private Plan plan;

    public User(Person person) {
        this.person = person;
    }
}
