package com.example.explorecalijpa.recommendation;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * This test does NOT start the Spring context.
 * It only tests the controller logic directly.
 */
class RecommendationControllerTest {

  private MockMvc mockMvc;

  @Mock
  private RecommendationService recommendationService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    RecommendationController controller = new RecommendationController(recommendationService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void testValidRequest() throws Exception {
    List<TourSummary> tours = Arrays.asList(
        new SimpleTourSummary(1, "Tour 1", 4.5, 10L),
        new SimpleTourSummary(2, "Tour 2", 4.7, 15L));

    when(recommendationService.getTopRatedTours(5)).thenReturn(tours);

    mockMvc.perform(get("/recommendations/top/5")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].tourId").value(1))
        .andExpect(jsonPath("$[1].title").value("Tour 2"));
  }

  @Test
  void testEmptyRecommendationsReturnsEmptyArray() throws Exception {
    when(recommendationService.getTopRatedTours(5)).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/recommendations/top/5")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  void testInvalidLimitLow() throws Exception {
    mockMvc.perform(get("/recommendations/top/0"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testInvalidLimitHigh() throws Exception {
    mockMvc.perform(get("/recommendations/top/101"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testDefaultLimit() throws Exception {
    when(recommendationService.getRecommendationsForCustomer(1, 5))
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/recommendations/customer/1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  static class SimpleTourSummary implements TourSummary {
    private final Integer tourId;
    private final String title;
    private final Double avgScore;
    private final Long reviewCount;

    SimpleTourSummary(Integer tourId, String title, Double avgScore, Long reviewCount) {
      this.tourId = tourId;
      this.title = title;
      this.avgScore = avgScore;
      this.reviewCount = reviewCount;
    }

    @Override
    public Integer getTourId() {
      return tourId;
    }

    @Override
    public String getTitle() {
      return title;
    }

    @Override
    public Double getAvgScore() {
      return avgScore;
    }

    @Override
    public Long getReviewCount() {
      return reviewCount;
    }
  }
}
