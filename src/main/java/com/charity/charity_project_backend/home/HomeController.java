package com.charity.charity_project_backend.home;

import com.charity.charity_project_backend.adopt.repositories.AdoptAnimalRepository;
import com.charity.charity_project_backend.auth.models.Role;
import com.charity.charity_project_backend.auth.repositories.UserRepository;
import com.charity.charity_project_backend.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final AdoptAnimalRepository adoptAnimalRepository;

    @GetMapping
    public ResponseEntity<HomeResponse> handler() {
        return ResponseEntity.ok(HomeResponse
                .builder()
                .petsFoundHomeCount(adoptAnimalRepository.countAdoptAnimalByOwnerIsNotNull())
                .partnersCount(userRepository.countUserByRole(Role.SPONSOR))
                .dollarsCollected(57000)
                .build());
    }
}
