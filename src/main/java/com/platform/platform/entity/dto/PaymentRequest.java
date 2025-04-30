package com.platform.platform.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull(message = "Amount is required")
    private Long amount; // in EGP (not piasters)

    private String description;
    private String studentId;
    private String courseId;

    @Pattern(regexp = "EGP", message = "Only EGP currency is supported")
    private String currency = "EGP";
}