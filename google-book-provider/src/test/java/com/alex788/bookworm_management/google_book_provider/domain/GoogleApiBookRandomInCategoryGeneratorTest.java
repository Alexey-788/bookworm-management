package com.alex788.bookworm_management.google_book_provider.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class GoogleApiBookRandomInCategoryGeneratorTest {

    Random random = mock(Random.class, withSettings().withoutAnnotations());
    RestTemplate restTemplate = mock(RestTemplate.class);
    ObjectMapper objectMapper = new ObjectMapper();
    GoogleApiBookRandomInCategoryGenerator generator = new GoogleApiBookRandomInCategoryGenerator(random, restTemplate, objectMapper);

    @Test
    void generate_ManyCandidates_ReturnsRightObject() {
        int index = 3;
        int candidateCount = 10;
        String category = "programming";
        String title = "Domain-Driven Design Distilled";
        setUpIndex(index);
        setUpTitleByIndex(title, index);
        setUpCandidateCountByCategory(candidateCount, category);

        GoogleApiBook book = generator.generate(category);

        assertThat(book.getCategory()).isEqualTo(category);
        assertThat(book.getCandidateCount()).isEqualTo(candidateCount);
        assertThat(book.getIndex()).isEqualTo(index);
        assertThat(book.getTitle()).isEqualTo(title);
    }

    @Test
    void generate_OneCandidate_ReturnsOnlyObject() {
        int index = 0;
        int candidateCount = 1;
        String category = "programming";
        String title = "Domain-Driven Design Distilled";
        setUpIndex(index);
        setUpTitleByIndex(title, index);
        setUpCandidateCountByCategory(candidateCount, category);

        GoogleApiBook book = generator.generate(category);

        assertThat(book.getCategory()).isEqualTo(category);
        assertThat(book.getCandidateCount()).isEqualTo(candidateCount);
        assertThat(book.getIndex()).isEqualTo(index);
        assertThat(book.getTitle()).isEqualTo(title);
    }

    void setUpIndex(int index) {
        when(random.nextInt(anyInt())).thenReturn(index);
    }

    void setUpTitleByIndex(String title, int index) {
        String titleJson = "{\"items\":[{\"volumeInfo\": {\"title\": \"" + title + "\"}}]}";
        when(restTemplate.getForEntity(contains("startIndex="+index), eq(String.class))).thenReturn(new ResponseEntity<>(titleJson, HttpStatus.OK));
    }

    void setUpCandidateCountByCategory(int candidateCount, String category) {
        String candidateCountJson = "{\"totalItems\": "+candidateCount+"}";
        when(restTemplate.getForEntity(and(contains("q=subject:"+category), not(contains("startIndex"))), eq(String.class)))
                .thenReturn(new ResponseEntity<>(candidateCountJson, HttpStatus.OK));
    }
}