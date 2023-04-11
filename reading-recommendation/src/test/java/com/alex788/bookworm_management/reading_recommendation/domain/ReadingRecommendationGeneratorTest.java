package com.alex788.bookworm_management.reading_recommendation.domain;

import com.alex788.bookworm_management.reading_recommendation.port.BookProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadingRecommendationGeneratorTest {

    BookProvider bookProvider = mock(BookProvider.class);
    ReadingRecommendationGenerator readingRecommendationGenerator = new ReadingRecommendationGenerator(bookProvider);

    @Test
    void generate_CreatesInstanceWithRandomBookFromSpecifiedCategory() {
        String category = "Some Category";
        String bookTitle = "Some Book";
        when(bookProvider.getRandomBookByCategory(category)).thenReturn(bookTitle);

        ReadingRecommendation readingRecommendation = readingRecommendationGenerator.generate(category);

        assertThat(readingRecommendation.getBookTitle()).isEqualTo(bookTitle);
    }
}