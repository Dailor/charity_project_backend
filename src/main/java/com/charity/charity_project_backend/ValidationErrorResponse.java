package com.charity.charity_project_backend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }
}