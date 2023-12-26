package com.charity.charity_project_backend.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    public Integer petsFoundHomeCount;
    public Integer partnersCount;
    public Integer dollarsCollected;
}
