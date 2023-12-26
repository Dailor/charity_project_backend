package com.charity.charity_project_backend.auth.services;

import com.charity.charity_project_backend.auth.exceptions.EmailUniqueException;
import com.charity.charity_project_backend.auth.models.Role;
import com.charity.charity_project_backend.auth.models.User;
import com.charity.charity_project_backend.auth.repositories.UserRepository;
import com.charity.charity_project_backend.auth.requests.AuthenticationRequest;
import com.charity.charity_project_backend.auth.requests.RegisterRequest;
import com.charity.charity_project_backend.auth.responses.AuthLoginResponse;
import com.charity.charity_project_backend.auth.responses.AuthRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthRegistrationResponse register(RegisterRequest request) throws EmailUniqueException {
        var userByEmail = repository.findByEmail(request.getEmail());

        if (userByEmail.isPresent()) {
            throw new EmailUniqueException();
        }


        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthRegistrationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthLoginResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthLoginResponse
                .builder()
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }
}
