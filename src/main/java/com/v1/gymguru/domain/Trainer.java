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
public class Trainer {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "TRAINER_DESCRIPTION")
    private String description;

    @Column(name = "EDUCATION")
    private String education;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Credential credential;

    @OneToMany(targetEntity = Subscription.class,
               mappedBy = "trainer",
               cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY
    )
    private List<Subscription> subscriptions;

    @OneToMany(targetEntity = Plan.class,
            mappedBy = "trainer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Plan> plans;

    public Trainer(String firstName, String lastName, String description, String education, Credential credential) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
        this.credential = credential;
    }

    public Trainer(Long id, String firstName, String lastName, String description, String education, Credential credential) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
        this.credential = credential;
    }
}
