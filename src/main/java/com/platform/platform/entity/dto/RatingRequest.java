package com.platform.platform.entity.dto;

public record RatingRequest(
    Integer studentId,
    Integer stars
) {
    // Validation can be added through compact constructor
    public RatingRequest {
        if (stars == null || stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars");
        }
        if (studentId == null || studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive number");
        }
    }
}