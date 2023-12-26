package com.charity.charity_project_backend.auth.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserNotFoundExceptionResponse {
    private final String msg = "User not found!";
}
