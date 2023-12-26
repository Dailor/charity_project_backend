package com.charity.charity_project_backend.adopt;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.adopt.requests.AdoptAnimalCreateRequest;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalListResponse;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalResponse;
import com.charity.charity_project_backend.adopt.services.AdoptAnimalService;
import com.charity.charity_project_backend.auth.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/adopt-animal")
@RequiredArgsConstructor
public class AdoptAnimalController {

    public final AdoptAnimalService service;

    @PutMapping("/")
    public ResponseEntity<String> addAdoptAnimal(Authentication authentication,
                                                 @Valid @RequestBody AdoptAnimalCreateRequest adoptAnimalCreateRequest) {
        User user = (User) authentication.getPrincipal();
        service.add(adoptAnimalCreateRequest, user);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/partner")
    public ResponseEntity<AdoptAnimalListResponse> getPartnersUnOwnedAnimal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                AdoptAnimalListResponse.builder()
                        .adoptAnimals(service.listBySponsor(user)
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
                        .build()
        );
    }
}
