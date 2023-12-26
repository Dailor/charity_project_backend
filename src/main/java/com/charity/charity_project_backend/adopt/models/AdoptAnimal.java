package com.charity.charity_project_backend.adopt.models;

import com.charity.charity_project_backend.auth.models.Role;
import com.charity.charity_project_backend.auth.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "adopt_animal"
)
public class AdoptAnimal {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String breed;

    @Column()
    private String photoUrl;

    @Column(nullable = false)
    private Integer old;

    @Column(nullable = false)
    private Integer weight;

    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    @Enumerated(EnumType.STRING)
    private AnimalType type;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", nullable = false)
    private User sponsor;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
