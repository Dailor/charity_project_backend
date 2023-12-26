package com.charity.charity_project_backend.adopt.repositories;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptAnimalRepository extends JpaRepository<AdoptAnimal, Integer> {
    List<AdoptAnimal> findAllBySponsorAndOwnerIsNull(User sponsor);
}
