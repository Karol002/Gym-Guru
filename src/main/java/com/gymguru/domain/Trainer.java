package com.gymguru.domain;

import com.gymguru.domain.enums.Specialization;
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
@Entity(name = "TRAINERS")
public class Trainer {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "TRAINER_DESCRIPTION", length = 500)
    private String description;

    @NotNull
    @Column(name = "EDUCATION")
    private String education;

    @NotNull
    @Column(name = "MONTH_PRICE")
    private BigDecimal monthPrice;

    @NotNull
    @Column(name = "SPECIALIZATION")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @JoinColumn(name = "CREDENTIAL_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Credential credential;

    @OneToMany(targetEntity = Subscription.class,
               mappedBy = "trainer",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY
    )
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(targetEntity = Plan.class,
            mappedBy = "trainer",
            cascade = CascadeType.ALL,
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
}
