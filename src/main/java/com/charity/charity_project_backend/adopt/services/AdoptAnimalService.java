package com.charity.charity_project_backend.adopt.services;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.adopt.repositories.AdoptAnimalRepository;
import com.charity.charity_project_backend.adopt.requests.AdoptAnimalCreateRequest;
import com.charity.charity_project_backend.auth.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptAnimalService {
    private final AdoptAnimalRepository repository;

    public void add(AdoptAnimalCreateRequest request, User sponsor) {
        AdoptAnimal adoptAnimal = AdoptAnimal.builder()
                .name(request.getName())
                .breed(request.getBreed())
                .photoUrl(request.getPhotoUrl())
                .old(request.getOld())
                .weight(request.getWeight())
                .gender(request.getGender())
                .type(request.getType())
                .sponsor(sponsor)
                .build();

        repository.save(adoptAnimal);
    }

    public List<AdoptAnimal> listBySponsor(User sponsor) {
        return repository.findAllBySponsorAndOwnerIsNull(sponsor);
    }
}
