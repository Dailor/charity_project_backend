package com.charity.charity_project_backend.adopt.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptAnimalListResponse {
    private List<AdoptAnimalResponse> adoptAnimals;
}
