package com.example.explorecalijpa.recommendation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import com.example.explorecalijpa.repo.TourRatingRepository;

/**
 * Unit tests for RecommendationService.
 * Verifies service behavior and mapping to repository.
 */
class RecommendationServiceTest {

  @Mock
  private TourRatingRepository tourRatingRepository;

  @InjectMocks
  private RecommendationService recommendationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetTopRatedTours_ReturnsEmptyList() {
    when(tourRatingRepository.findTopTours(PageRequest.of(0, 5)))
        .thenReturn(Collections.emptyList());

    List<TourSummary> result = recommendationService.getTopRatedTours(5);

    assertNotNull(result);
    assertTrue(result.isEmpty(), "Expected an empty list when no results exist");
    verify(tourRatingRepository).findTopTours(PageRequest.of(0, 5));
  }

  @Test
  void testGetTopRatedTours_ReturnsResults() {
    TourSummary mockSummary = mock(TourSummary.class);
    when(tourRatingRepository.findTopTours(PageRequest.of(0, 3)))
        .thenReturn(List.of(mockSummary));

    List<TourSummary> result = recommendationService.getTopRatedTours(3);

    assertEquals(1, result.size());
    verify(tourRatingRepository).findTopTours(PageRequest.of(0, 3));
  }

  @Test
  void testGetRecommendationsForCustomer_ReturnsEmptyList() {
    when(tourRatingRepository.findRecommendedForCustomer(1, PageRequest.of(0, 5)))
        .thenReturn(Collections.emptyList());

    List<TourSummary> result = recommendationService.getRecommendationsForCustomer(1, 5);

    assertNotNull(result);
    assertTrue(result.isEmpty(), "Expected empty list for no recommendations");
    verify(tourRatingRepository).findRecommendedForCustomer(1, PageRequest.of(0, 5));
  }

  @Test
  void testGetRecommendationsForCustomer_ReturnsResults() {
    TourSummary mockSummary = mock(TourSummary.class);
    when(tourRatingRepository.findRecommendedForCustomer(2, PageRequest.of(0, 3)))
        .thenReturn(List.of(mockSummary));

    List<TourSummary> result = recommendationService.getRecommendationsForCustomer(2, 3);

    assertEquals(1, result.size());
    verify(tourRatingRepository).findRecommendedForCustomer(2, PageRequest.of(0, 3));
  }
}
