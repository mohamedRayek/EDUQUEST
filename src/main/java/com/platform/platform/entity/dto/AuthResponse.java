package com.platform.platform.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;  // Added role information
    private Integer userId;  // Added user ID

    public AuthResponse(String accessToken, String role, Integer userId) {
        this.accessToken = accessToken;
        this.role = role;
        this.userId = userId;
    }
}
