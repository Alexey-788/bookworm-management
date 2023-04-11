package com.alex788.bookworm_management.reading_recommendation.service;

import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendation;
import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendationGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReadingRecommendationService {

    private final ReadingRecommendationGenerator readingRecommendationGenerator;

    public ReadingRecommendation getReadingRecommendation(String category) {
        return readingRecommendationGenerator.generate(category);
    }
}
