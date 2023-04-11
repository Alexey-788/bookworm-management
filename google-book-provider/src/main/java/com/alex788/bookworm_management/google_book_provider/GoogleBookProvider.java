package com.alex788.bookworm_management.google_book_provider;

import com.alex788.bookworm_management.reading_recommendation.port.BookProvider;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
public class GoogleBookProvider implements BookProvider { // TODO: все что здесь происходит мало напоминает ооп. Из состояний одни инструменты, а это ховно.

    private final static String BOOK_COUNT_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?maxResults=1&q=subject:${category}";
    private final static String BOOK_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?maxResults=1&startIndex=${index}&q=subject:${category}";

    private final Random random;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @SneakyThrows // TODO: customize exception
    @Override
    public String getRandomBookByCategory(String category) {
//        return getBookTitleByCategoryAndIndex(category, index);
        return null;
    }

    @SneakyThrows // TODO: customize exception
    private int getBookCountByCategory(String category) {
        String countUrl = getBookCountUrl(category);
        ResponseEntity<String> countResponseEntity = restTemplate.getForEntity(countUrl, String.class);
        String countJson = countResponseEntity.getBody();
        JsonNode countTreeNode = objectMapper.readTree(countJson);
        JsonPointer countJsonPointer = JsonPointer.compile("/totalItems");
        JsonNode countNode = countTreeNode.at(countJsonPointer);
        return countNode.asInt();
    }

    @SneakyThrows // TODO: customize exception
    private String getBookTitleByCategoryAndIndex(String category, int index) {
        String booksUrl = getBookUrl(category, index);
        ResponseEntity<String> bookResponseEntity = restTemplate.getForEntity(booksUrl, String.class);
        String bookJson = bookResponseEntity.getBody();
        JsonNode bookTreeNode = objectMapper.readTree(bookJson);
        JsonPointer bookJsonPointer = JsonPointer.compile("/items/0/volumeInfo/title");
        JsonNode bookTitleNode = bookTreeNode.at(bookJsonPointer);
        return bookTitleNode.asText();
    }

    private String getBookCountUrl(String category) {
        Map<String, String> urlParams = Map.of("category", category);
        return StringSubstitutor.replace(BOOK_COUNT_URL_TEMPLATE, urlParams);
    }

    private String getBookUrl(String category, int index) {
        Map<String, String> urlParams = Map.of(
                "index", String.valueOf(index),
                "category", category
        );
        return StringSubstitutor.replace(BOOK_URL_TEMPLATE, urlParams);
    }
}
