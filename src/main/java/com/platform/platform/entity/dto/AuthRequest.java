package com.platform.platform.entity.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
