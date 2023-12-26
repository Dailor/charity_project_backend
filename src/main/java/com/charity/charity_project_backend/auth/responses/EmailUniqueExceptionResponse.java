package com.charity.charity_project_backend.auth.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailUniqueExceptionResponse {
    private final String email = "User with this username exists!";
}
