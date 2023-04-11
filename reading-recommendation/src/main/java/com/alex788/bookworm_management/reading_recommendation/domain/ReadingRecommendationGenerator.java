package com.alex788.bookworm_management.reading_recommendation.domain;

import com.alex788.bookworm_management.reading_recommendation.port.BookProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReadingRecommendationGenerator {

    private final BookProvider bookProvider;

    public ReadingRecommendation generate(String category) {
        return new ReadingRecommendation(bookProvider.getRandomBookByCategory(category));
    }
}
