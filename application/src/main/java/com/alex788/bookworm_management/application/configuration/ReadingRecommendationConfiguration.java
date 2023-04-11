package com.alex788.bookworm_management.application.configuration;

import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendationGenerator;
import com.alex788.bookworm_management.reading_recommendation.port.BookProvider;
import com.alex788.bookworm_management.reading_recommendation.rest.ReadingRecommendationController;
import com.alex788.bookworm_management.reading_recommendation.service.ReadingRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ReadingRecommendationConfiguration {

    @Bean
    public BookProvider bookProvider() {
        log.warn("Using stab book provider! Implement custom one for production!");
        return (category) -> category + " Book";
    }

    @Bean
    public ReadingRecommendationGenerator readingRecommendationGenerator(BookProvider bookProvider) {
        return new ReadingRecommendationGenerator(bookProvider);
    }

    @Bean
    public ReadingRecommendationService readingRecommendationService(ReadingRecommendationGenerator readingRecommendationGenerator) {
        return new ReadingRecommendationService(readingRecommendationGenerator);
    }

    @Bean
    public ReadingRecommendationController readingRecommendationController(ReadingRecommendationService readingRecommendationService) {
        return new ReadingRecommendationController(readingRecommendationService);
    }
}
