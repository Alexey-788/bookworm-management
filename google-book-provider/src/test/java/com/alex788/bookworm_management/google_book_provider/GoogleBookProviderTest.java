package com.alex788.bookworm_management.google_book_provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GoogleBookProviderTest {

    Random random = mock(Random.class, withSettings().withoutAnnotations());
    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = mock(RestTemplate.class);
    GoogleBookProvider googleBookProvider = new GoogleBookProvider(random, objectMapper, restTemplate);


    @Test
    void getRandomBookByCategory_OneBookInResponse_ReturnsOnlyPossibleBookTitle() {
        String category = "programming";
        String expectedBookTitle = "Domain-Driven Design Distilled";
        int bookIndex = 0;
        int bookCount = 1;
        setUpBookCountForCategory(bookCount, category);
        setUpBookTitleByIndex(expectedBookTitle, bookIndex);
        when(random.nextInt(anyInt())).thenReturn(bookIndex);

        String actualBookTitle = googleBookProvider.getRandomBookByCategory(category);

        assertThat(actualBookTitle).isEqualTo(expectedBookTitle);
    }


    @Test
    void getRandomBookByCategory_ManyBookInResponse_ReturnsRandomBookTitle() {
        String category = "programming";
        String expectedBookTitle = "Domain-Driven Design Distilled";
        int bookIndex = 2;
        int bookCount = 5;
        setUpBookCountForCategory(bookCount, category);
        setUpBookTitleByIndex(expectedBookTitle, bookIndex);
        when(random.nextInt(anyInt())).thenReturn(bookIndex);

        String actualBookTitle = googleBookProvider.getRandomBookByCategory(category);

        assertThat(actualBookTitle).isEqualTo(expectedBookTitle);
    }

    void setUpBookCountForCategory(int count, String category) {
        String bookCountResponse = bookCountResponse(count);
        when(restTemplate.getForEntity(contains("q=subject:"+category), eq(String.class))).thenReturn(new ResponseEntity<>(bookCountResponse, HttpStatus.OK));
    }

    void setUpBookTitleByIndex(String bookTitle, int index) {
        String bookTitleResponse = bookTitleResponse(bookTitle);
        when(restTemplate.getForEntity(contains("startIndex="+index), eq(String.class))).thenReturn(new ResponseEntity<>(bookTitleResponse, HttpStatus.OK));
    }

    String bookTitleResponse(String bookTitle) {
        return """
                   {
                     "items": [
                       {
                         "volumeInfo": {"title": "%s"}
                       }
                     ]
                   }
                """.formatted(bookTitle);
    }

    String bookCountResponse(int count) {
        return """
                   {
                     "totalItems": %d
                   }
                """.formatted(count);
    }
}