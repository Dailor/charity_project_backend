package com.charity.charity_project_backend.auth;


import com.charity.charity_project_backend.auth.exceptions.EmailUniqueException;
import com.charity.charity_project_backend.auth.models.User;
import com.charity.charity_project_backend.auth.requests.AuthenticationRequest;
import com.charity.charity_project_backend.auth.requests.RegisterRequest;
import com.charity.charity_project_backend.auth.responses.*;
import com.charity.charity_project_backend.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthRegistrationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) throws EmailUniqueException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthLoginResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                MeResponse
                        .builder()
                        .email(user.getEmail())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .build());
    }

    @ExceptionHandler(EmailUniqueException.class)
    public ResponseEntity<EmailUniqueExceptionResponse> handleEmailUniqueException(EmailUniqueException ex) {
        return new ResponseEntity<>(EmailUniqueExceptionResponse.builder().build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class, NoSuchElementException.class})
    public ResponseEntity<UserNotFoundExceptionResponse> handleCredentialException(Exception ex){
        return new ResponseEntity<>(UserNotFoundExceptionResponse.builder().build(), HttpStatus.NOT_FOUND);
    }

    @InitBinder
    public void initialBinderForTrimmingSpaces(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimEditor);
    }
}
