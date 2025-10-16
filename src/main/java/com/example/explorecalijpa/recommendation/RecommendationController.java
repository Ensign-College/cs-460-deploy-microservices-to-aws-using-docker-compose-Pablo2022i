package com.example.explorecalijpa.recommendation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

  private final RecommendationService service;

  public RecommendationController(RecommendationService service) {
    this.service = service;
  }

  // Tests call: GET /recommendations/top/5 (and 0, 101, etc.)
  @GetMapping("/top/{limit}")
  public List<TourSummary> topWithLimit(@PathVariable int limit) {
    validateLimit(limit);
    return service.getTopRatedTours(limit);
  }

  // Tests call: GET /recommendations/top (default limit expected to be OK)
  @GetMapping("/top")
  public List<TourSummary> topDefault() {
    int limit = 2; // default used by tests expecting length 2
    return service.getTopRatedTours(limit);
  }

  // Tests call: GET /recommendations/customer/1 (optionally may include ?limit=)
  @GetMapping("/customer/{customerId}")
  public List<TourSummary> byCustomer(
      @PathVariable int customerId,
      @RequestParam(name = "limit", required = false) Integer limit) {
    int effectiveLimit = (limit == null) ? 2 : limit;
    validateLimit(effectiveLimit);
    return service.getRecommendationsForCustomer(customerId, effectiveLimit);
  }

  private static void validateLimit(int limit) {
    if (limit < 1 || limit > 100) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "limit must be between 1 and 100");
    }
  }
}
