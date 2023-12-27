package com.charity.charity_project_backend.adopt;

import com.charity.charity_project_backend.adopt.models.AdoptAnimal;
import com.charity.charity_project_backend.adopt.requests.AdoptAnimalRequest;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalListResponse;
import com.charity.charity_project_backend.adopt.responses.AdoptAnimalResponse;
import com.charity.charity_project_backend.adopt.services.AdoptAnimalService;
import com.charity.charity_project_backend.auth.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adopt-animal")
@RequiredArgsConstructor
public class AdoptAnimalController {

    public final AdoptAnimalService service;

    @GetMapping("/{idx}")
    public ResponseEntity<AdoptAnimalResponse> getAdoptAnimal(@PathVariable("idx") Integer idx) {
        AdoptAnimal adoptAnimal = service.getByIdx(idx);

        return ResponseEntity.ok(AdoptAnimalResponse.builder()
                .name(adoptAnimal.getName())
                .breed(adoptAnimal.getBreed())
                .photoUrl(adoptAnimal.getPhotoUrl())
                .old(adoptAnimal.getOld())
                .weight(adoptAnimal.getWeight())
                .gender(adoptAnimal.getGender())
                .type(adoptAnimal.getType())
                .build());
    }

    @PutMapping("/")
    public ResponseEntity<String> addAdoptAnimal(Authentication authentication,
                                                 @Valid @RequestBody AdoptAnimalRequest adoptAnimalRequest) {
        User user = (User) authentication.getPrincipal();
        service.add(adoptAnimalRequest, user);

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/{idx}")
    public ResponseEntity<String> editAdoptAnimal(
            @PathVariable("idx") Integer idx,
            Authentication authentication,
            @Valid @RequestBody AdoptAnimalRequest adoptAnimalRequest) {

        User user = (User) authentication.getPrincipal();
        service.editBySponsor(idx, adoptAnimalRequest, user);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/partner")
    public ResponseEntity<AdoptAnimalListResponse> getPartnersUnOwnedAnimal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<AdoptAnimal> adoptAnimalList = service.listBySponsor(user);

        return ResponseEntity.ok(
                AdoptAnimalHelper.fromListToResponseList(adoptAnimalList)
        );
    }

    @GetMapping("/adopt")
    public ResponseEntity<AdoptAnimalListResponse> getAdoptAllowAnimals() {
        List<AdoptAnimal> adoptAnimalList = service.listCanBeAdopt();

        return ResponseEntity.ok(
                AdoptAnimalHelper.fromListToResponseList(adoptAnimalList)
        );
    }

    @PostMapping("/adopt/{idx}")
    public ResponseEntity<String> adoptAnimal(
            @PathVariable("idx") Integer idx,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        service.adoptAnimal(idx, user);

        return ResponseEntity.ok("ok");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handler(HttpClientErrorException e) {
        return new ResponseEntity<String>("error", e.getStatusCode());
    }

    @GetMapping("/adopted")
    public ResponseEntity<AdoptAnimalListResponse> getPartnersAdoptedAnimal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<AdoptAnimal> adoptAnimalList = service.listBySponsorAdopted(user);

        return ResponseEntity.ok(
                AdoptAnimalHelper.fromListToResponseList(adoptAnimalList)
        );
    }

    @GetMapping("/user")
    public ResponseEntity<AdoptAnimalListResponse> getUsersAdoptedAnimal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<AdoptAnimal> adoptAnimalList = service.listByUserAdopted(user);

        return ResponseEntity.ok(
                AdoptAnimalHelper.fromListToResponseList(adoptAnimalList)
        );
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<String> deleteAdoptAnimal(
            @PathVariable("idx") Integer idx,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        service.deletePartnerAnimal(idx, user);

        return ResponseEntity.ok("ok");
    }
}
