package com.charity.charity_project_backend.auth.responses;

import com.charity.charity_project_backend.auth.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponse {
    private String token;
    private Role role;
}
