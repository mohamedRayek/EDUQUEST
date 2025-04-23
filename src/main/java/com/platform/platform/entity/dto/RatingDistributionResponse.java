package com.platform.platform.entity.dto;

public record RatingDistributionResponse(
    int oneStars,
    int twoStars,
    int threeStars,
    int fourStars,
    int fiveStars,
    float averageRating
) {

    public int totalRatings() {
        return oneStars + twoStars + threeStars + fourStars + fiveStars;
    }

    public RatingDistributionResponse {
        if (oneStars < 0 || twoStars < 0 || threeStars < 0 || 
            fourStars < 0 || fiveStars < 0) {
            throw new IllegalArgumentException("Rating counts cannot be negative");
        }
        if (averageRating < 0 || averageRating > 5) {
            throw new IllegalArgumentException("Average rating must be between 0 and 5");
        }
    }
}