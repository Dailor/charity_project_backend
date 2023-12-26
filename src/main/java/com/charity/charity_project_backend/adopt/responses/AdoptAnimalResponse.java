package com.charity.charity_project_backend.adopt.responses;

import com.charity.charity_project_backend.adopt.models.AnimalGender;
import com.charity.charity_project_backend.adopt.models.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptAnimalResponse {
    private Integer id;
    private String name;
    private String breed;
    private String photoUrl;

    private Integer old;

    private Integer weight;

    private AnimalGender gender;

    AnimalType type;
}
