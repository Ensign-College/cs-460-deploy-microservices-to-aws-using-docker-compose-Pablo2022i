package com.example.explorecalijpa.recommendation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;

  @Autowired
  public RecommendationController(RecommendationService recommendationService) {
    this.recommendationService = recommendationService;
  }

  @GetMapping("/top/{limit}")
  public List<TourSummary> getTopTours(@PathVariable int limit) {
    return recommendationService.getTopRatedTours(limit);
  }

  @GetMapping("/customer/{customerId}")
  public List<TourSummary> getRecommendations(@PathVariable int customerId,
      @RequestParam(defaultValue = "5") int limit) {
    return recommendationService.getRecommendationsForCustomer(customerId, limit);
  }
}
