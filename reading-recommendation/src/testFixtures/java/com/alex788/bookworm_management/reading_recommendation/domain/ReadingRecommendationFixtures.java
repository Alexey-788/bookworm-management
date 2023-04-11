package com.alex788.bookworm_management.reading_recommendation.domain;

import java.util.UUID;

public class ReadingRecommendationFixtures {

    public static ReadingRecommendation readingRecommendationWithRandomBookTitle() {
        return new ReadingRecommendation(UUID.randomUUID().toString());
    }
}
