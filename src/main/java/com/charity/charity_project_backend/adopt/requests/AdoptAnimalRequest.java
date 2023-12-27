package com.charity.charity_project_backend.adopt.requests;

import com.charity.charity_project_backend.adopt.models.AnimalGender;
import com.charity.charity_project_backend.adopt.models.AnimalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptAnimalRequest {
    @NotNull
    private String name;
    private String breed;
    private String photoUrl;

    @NotNull
    private Integer old;

    @NotNull
    private Integer weight;

    @NotNull
    private AnimalGender gender;

    @NotNull AnimalType type;
}
