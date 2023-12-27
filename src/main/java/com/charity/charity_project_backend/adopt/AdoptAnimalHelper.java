package com.charity.charity_project_backend.adopt;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalListResponse;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalResponse;

import java.util.List;

public class AdoptAnimalHelper {
    public static AdoptAnimalListResponse fromListToResponseList(List<AdoptAnimal> adoptAnimalList){
        return AdoptAnimalListResponse.builder()
                .adoptAnimals(adoptAnimalList
                        .stream()
                        .map(adoptAnimal -> AdoptAnimalResponse
                                .builder()
                                .id(adoptAnimal.getId())
                                .name(adoptAnimal.getName())
                                .breed(adoptAnimal.getBreed())
                                .photoUrl(adoptAnimal.getPhotoUrl())
                                .old(adoptAnimal.getOld())
                                .weight(adoptAnimal.getWeight())
                                .type(adoptAnimal.getType())
                                .gender(adoptAnimal.getGender())
                                .build())
                        .toList())
                .build();
    }
}
