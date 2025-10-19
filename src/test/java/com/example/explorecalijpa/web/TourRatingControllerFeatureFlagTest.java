package com.example.explorecalijpa.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.explorecalijpa.business.TourRatingService;

// Checks that feature flag disables rating endpoints
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "features.tour-ratings=false")
public class TourRatingControllerFeatureFlagTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private TourRatingService serviceMock;

  @Test
  void ratingsDisabledReturnsNotFound() {
    // use admin credentials to bypass security
    TestRestTemplate admin = restTemplate.withBasicAuth("admin", "admin123");
    ResponseEntity<String> res = admin.getForEntity("/tours/999/ratings", String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }
}
