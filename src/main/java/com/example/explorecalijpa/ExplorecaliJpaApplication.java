package com.example.explorecalijpa;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.explorecalijpa.business.TourPackageService;
import com.example.explorecalijpa.business.TourService;
import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.repo.TourRatingRepository;
import com.example.explorecalijpa.repo.TourRepository;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ExplorecaliJpaApplication implements CommandLineRunner {

    @Bean
    public OpenAPI swaggerHeader() {
        return new OpenAPI()
                .info((new Info())
                        .description("Services for the Explore California Relational Database.")
                        .title(StringUtils.substringBefore(getClass().getSimpleName(), "$"))
                        .version("3.0.0"));
    }

    @Autowired
    private TourPackageService tourPackageService;

    @Autowired
    private TourService tourService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourRatingRepository tourRatingRepository;

    public static void main(String[] args) {
        SpringApplication.run(ExplorecaliJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Persisted Packages = " + tourPackageService.total());
        System.out.println("Persisted Tours = " + tourService.total());

        // Initialize sample ratings if the database is empty
        Tour firstTour = tourRepository.findAll().stream().findFirst().orElse(null);
        if (firstTour != null && tourRatingRepository.count() == 0) {
            tourRatingRepository.save(new TourRating(firstTour, 1, 5, "Amazing!"));
            tourRatingRepository.save(new TourRating(firstTour, 2, 4, "Very good!"));
            tourRatingRepository.save(new TourRating(firstTour, 3, 3, "Good, but could be better."));
            System.out.println("Sample Tour Ratings added successfully.");
        } else {
            System.out.println("Tour ratings already exist or no tours found.");
        }
    }
}
