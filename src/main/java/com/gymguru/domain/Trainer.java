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

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "TRAINER_DESCRIPTION")
    private String description;

    @Column(name = "EDUCATION")
    private String education;

    @OneToMany(targetEntity = Subscription.class,
               mappedBy = "trainer",
               cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY
    )
    private List<Subscription> subscriptions;

    public Trainer(String email, String password, String firstName, String lastName, String description, String education) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
    }

    public Trainer(Long id, String email, String password, String firstName, String lastName, String description, String education) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
    }
}
