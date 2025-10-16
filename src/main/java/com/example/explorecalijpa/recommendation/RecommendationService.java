package com.example.explorecalijpa.recommendation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.explorecalijpa.repo.TourRatingRepository;

@Service
public class RecommendationService {

  private final TourRatingRepository tourRatingRepository;

  @Autowired
  public RecommendationService(TourRatingRepository tourRatingRepository) {
    this.tourRatingRepository = tourRatingRepository;
  }

  public List<TourSummary> getTopRatedTours(int limit) {
    return tourRatingRepository.findTopTours(PageRequest.of(0, limit));
  }

  public List<TourSummary> getRecommendationsForCustomer(int customerId, int limit) {
    return tourRatingRepository.findRecommendedForCustomer(customerId, PageRequest.of(0, limit));
  }

  // --- Methods expected by RecommendationController & its tests ---

  /** Alias expected by the controller/tests. */
  public List<TourSummary> top(int limit) {
    return getTopRatedTours(limit);
  }

  /** Alias expected by the controller/tests. */
  public List<TourSummary> byCustomer(int customerId, int limit) {
    return getRecommendationsForCustomer(customerId, limit);
  }
}
