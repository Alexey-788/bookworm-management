package com.alex788.bookworm_management.reading_recommendation.service;

import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendation;
import com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendationGenerator;
import org.junit.jupiter.api.Test;

import static com.alex788.bookworm_management.reading_recommendation.domain.ReadingRecommendationFixtures.readingRecommendationWithRandomBookTitle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadingRecommendationServiceTest {

    ReadingRecommendationGenerator readingRecommendationGenerator = mock(ReadingRecommendationGenerator.class);
    ReadingRecommendationService readingRecommendationService = new ReadingRecommendationService(readingRecommendationGenerator);

    @Test
    void getReadingRecommendation_GeneratesRecommendationWithGenerator() {
        String category = "Some Category";
        ReadingRecommendation expectedReadingRecommendation = readingRecommendationWithRandomBookTitle();
        when(readingRecommendationGenerator.generate(category)).thenReturn(expectedReadingRecommendation);

        ReadingRecommendation actualReadingRecommendation = readingRecommendationService.getReadingRecommendation(category);

        assertThat(actualReadingRecommendation).isSameAs(expectedReadingRecommendation);
    }
}