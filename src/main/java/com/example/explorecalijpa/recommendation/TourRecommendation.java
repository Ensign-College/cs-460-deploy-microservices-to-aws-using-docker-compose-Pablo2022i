package com.example.explorecalijpa.recommendation;

/**
 * Simple data transfer object (DTO)
 * representing a recommended tour.
 */
public record TourRecommendation(
    Integer tourId,
    String title,
    Double averageScore,
    Long reviewCount) {
}
