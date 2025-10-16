package com.example.explorecalijpa.web;

import com.example.explorecalijpa.model.*;
import com.example.explorecalijpa.repo.TourPackageRepository;
import com.example.explorecalijpa.repo.TourRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tour Controller — handles creating and listing tours.
 */
@RestController
@RequestMapping("/tours")
public class TourController {

  private final TourRepository tourRepository;
  private final TourPackageRepository tourPackageRepository;

  public TourController(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
    this.tourRepository = tourRepository;
    this.tourPackageRepository = tourPackageRepository;
  }

  /**
   * Create a new Tour.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Tour createTour(@RequestBody TourDto dto) {
    TourPackage tourPackage = tourPackageRepository.findById(dto.getTourPackageCode())
        .orElseThrow(() -> new NoSuchElementException("Tour package does not exist: " + dto.getTourPackageCode()));

    Tour tour = new Tour(
        dto.getTitle(),
        dto.getDescription(),
        dto.getBlurb(),
        dto.getPrice(),
        dto.getDuration(),
        dto.getBullets(),
        dto.getKeywords(),
        tourPackage,
        dto.getDifficulty(),
        dto.getRegion());

    return tourRepository.save(tour);
  }

  /**
   * List all tours.
   */
  @GetMapping
  public List<Tour> getAllTours() {
    return tourRepository.findAll();
  }
}
