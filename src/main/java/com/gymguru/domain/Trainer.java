package com.gymguru.domain;

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
public class Trainer {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "TRAINER_DESCRIPTION")
    private String description;

    @Column(name = "EDUCATION")
    private String education;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @OneToMany(targetEntity = Subscription.class,
               mappedBy = "trainer",
               cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY
    )
    private List<Subscription> subscriptions;

    public Trainer(String description, String education, Person person) {
        this.description = description;
        this.education = education;
        this.person = person;
    }

    public Trainer(Long id, String description, String education, Person person) {
        this.id = id;
        this.description = description;
        this.education = education;
        this.person = person;
    }
}
