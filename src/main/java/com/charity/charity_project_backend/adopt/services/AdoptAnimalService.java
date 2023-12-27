package com.charity.charity_project_backend.adopt.services;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.adopt.models.AnimalGender;
import com.charity.charity_project_backend.adopt.repositories.AdoptAnimalRepository;
import com.charity.charity_project_backend.adopt.requests.AdoptAnimalRequest;
import com.charity.charity_project_backend.auth.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdoptAnimalService {
    private final AdoptAnimalRepository repository;

    public AdoptAnimal getByIdx(Integer idx) {
        return repository.findById(idx).orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404)));
    }

    public void editBySponsor(Integer idx, AdoptAnimalRequest request, User sponsor) {
        AdoptAnimal adoptAnimal = this.getByIdx(idx);

        if (!adoptAnimal.getSponsor().equals(sponsor) || adoptAnimal.getOwner() != null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403));
        }

        adoptAnimal.setName(request.getName());
        adoptAnimal.setBreed(request.getBreed());
        adoptAnimal.setPhotoUrl(request.getPhotoUrl());
        adoptAnimal.setOld(request.getOld());
        adoptAnimal.setWeight(request.getWeight());
        adoptAnimal.setGender(request.getGender());
        adoptAnimal.setType(request.getType());

        repository.save(adoptAnimal);
    }

    public void add(AdoptAnimalRequest request, User sponsor) {
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

    public List<AdoptAnimal> listCanBeAdopt() {
        return repository.findAllByOwnerIsNull();
    }

    public List<AdoptAnimal> listBySponsorAdopted(User sponsor) {
        return repository.findAllBySponsorAndOwnerIsNotNull(sponsor);
    }

    public List<AdoptAnimal> listByUserAdopted(User owner){
        return repository.findAllByOwner(owner);
    }

    public void adoptAnimal(Integer idx, User user) {
        AdoptAnimal adoptAnimal = this.getByIdx(idx);

        if (adoptAnimal.getOwner() != null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }

        adoptAnimal.setOwner(user);

        repository.save(adoptAnimal);
    }

    public void deletePartnerAnimal(Integer idx, User sponsor){
        AdoptAnimal adoptAnimal = this.getByIdx(idx);

        if (!adoptAnimal.getSponsor().equals(sponsor) || adoptAnimal.getOwner() != null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403));
        }

        repository.delete(adoptAnimal);
    }
}
