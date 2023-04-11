package com.alex788.bookworm_management.reading_recommendation.rest;

import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendation;
import com.alex788.bookworm_management.reading_recommendation.service.ReadingRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReadingRecommendationController {

    private final ReadingRecommendationService readingRecommendationService;

    @GetMapping("/rest/v1/books/recommendations/random")
    public ReadingRecommendation readingRecommendation(@RequestParam String category) {
        return readingRecommendationService.getReadingRecommendation(category);
    }
}
