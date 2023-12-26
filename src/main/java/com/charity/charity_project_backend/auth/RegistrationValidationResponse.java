package com.charity.charity_project_backend.auth;

import lombok.Data;

@Data
public class RegistrationValidationResponse {
    private String email = "User with this username exists!";
}