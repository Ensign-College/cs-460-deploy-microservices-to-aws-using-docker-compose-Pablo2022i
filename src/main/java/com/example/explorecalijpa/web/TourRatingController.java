package com.example.explorecalijpa.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.config.FeatureFlagService;
import com.example.explorecalijpa.model.TourRating;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Tour Rating", description = "Tour Rating API")
@RequestMapping("/tours/{tourId}/ratings")
public class TourRatingController {
  private final TourRatingService tourRatingService;
  private final FeatureFlagService featureFlagService;

  // Constructor for dependencies
  public TourRatingController(TourRatingService tourRatingService,
      FeatureFlagService featureFlagService) {
    this.tourRatingService = tourRatingService;
    this.featureFlagService = featureFlagService;
  }

  // Check if ratings feature is on
  private void checkRatingsEnabled() {
    if (!featureFlagService.isEnabled("tour-ratings")) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature off");
    }
  }

  // Create a new rating
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a Tour Rating")
  public RatingDto createTourRating(@PathVariable int tourId, @RequestBody @Valid RatingDto dto) {
    checkRatingsEnabled();
    log.info("POST /tours/{}/ratings", tourId);
    var rating = tourRatingService.createNew(tourId, dto.getCustomerId(), dto.getScore(), dto.getComment());
    return new RatingDto(rating);
  }

  // Get all ratings
  @GetMapping
  @Operation(summary = "Get All Ratings")
  public List<RatingDto> getAllRatingsForTour(@PathVariable int tourId) {
    checkRatingsEnabled();
    log.info("GET /tours/{}/ratings", tourId);
    return tourRatingService.lookupRatings(tourId).stream().map(RatingDto::new).toList();
  }

  // Get average rating
  @GetMapping("/average")
  @Operation(summary = "Get Average")
  public Map<String, Double> getAverage(@PathVariable int tourId) {
    checkRatingsEnabled();
    log.info("GET /tours/{}/ratings/average", tourId);
    return Map.of("average", tourRatingService.getAverageScore(tourId));
  }

  // Update full rating
  @PutMapping
  @Operation(summary = "Update All Fields")
  public RatingDto updateWithPut(@PathVariable int tourId, @RequestBody @Valid RatingDto dto) {
    checkRatingsEnabled();
    log.info("PUT /tours/{}/ratings", tourId);
    return new RatingDto(tourRatingService.update(tourId, dto.getCustomerId(), dto.getScore(), dto.getComment()));
  }

  // Update partial rating
  @PatchMapping
  @Operation(summary = "Update Some Fields")
  public RatingDto updateWithPatch(@PathVariable int tourId, @RequestBody @Valid RatingDto dto) {
    checkRatingsEnabled();
    log.info("PATCH /tours/{}/ratings", tourId);
    return new RatingDto(tourRatingService.updateSome(tourId, dto.getCustomerId(),
        Optional.ofNullable(dto.getScore()), Optional.ofNullable(dto.getComment())));
  }

  // Delete a rating
  @DeleteMapping("/{customerId}")
  @Operation(summary = "Delete Rating")
  public void delete(@PathVariable int tourId, @PathVariable int customerId) {
    checkRatingsEnabled();
    log.info("DELETE /tours/{}/ratings/{}", tourId, customerId);
    tourRatingService.delete(tourId, customerId);
  }

  // Create multiple ratings at once
  @PostMapping("/batch")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Batch Ratings")
  public void createManyTourRatings(@PathVariable int tourId,
      @RequestParam int score,
      @RequestBody List<Integer> customers) {
    checkRatingsEnabled();
    log.info("POST /tours/{}/ratings/batch", tourId);
    tourRatingService.rateMany(tourId, score, customers);
  }
}
