package com.v1.gymguru.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Column(name = "TRAINER_DESCRIPTION", length = 500)
    private String description;

    @Column(name = "EDUCATION")
    private String education;

    @Column(name = "MONTH_PRICE")
    private BigDecimal monthPrice;

    @Column(name = "SPECIALIZATION")
    private Specialization specialization;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private Credential credential;

    @OneToMany(targetEntity = Subscription.class,
               mappedBy = "trainer",
               cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY
    )
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(targetEntity = Plan.class,
            mappedBy = "trainer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Plan> plans = new ArrayList<>();

    public Trainer(String firstName, String lastName, String description, String education, BigDecimal monthPrice, Specialization specialization, Credential credential) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
        this.monthPrice = monthPrice;
        this.specialization = specialization;
        this.credential = credential;
    }

    public Trainer(Long id, String firstName, String lastName, String description, String education, BigDecimal monthPrice, Specialization specialization, Credential credential) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
        this.monthPrice = monthPrice;
        this.specialization = specialization;
        this.credential = credential;
    }
}
