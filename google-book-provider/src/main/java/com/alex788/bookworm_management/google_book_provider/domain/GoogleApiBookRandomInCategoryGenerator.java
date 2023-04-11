package com.alex788.bookworm_management.google_book_provider.domain;

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
public class GoogleApiBookRandomInCategoryGenerator {
asdfasdadsfas
    private final static String CANDIDATE_COUNT_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?maxResults=1&q=subject:${category}";
    private final static String BOOK_URL_TEMPLATE = "https://www.googleapis.com/books/v1/volumes?maxResults=1&startIndex=${index}&q=subject:${category}";

    private final Random random;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GoogleApiBook generate(String category) {
        int candidateCount = getCandidateCount(category);
        int index = getIndex(candidateCount);
        String title = getTitle(category, index);
        return new GoogleApiBook(title, category, index, candidateCount);
    }

    private int getCandidateCount(String category) {
        String url = getCandidateCountUrl(category);
        String response = getCandidateCountResponse(url);
        return getCandidateCount2(response);
    }

    private String getCandidateCountUrl(String category) {
        Map<String, String> urlParams = Map.of("category", category);
        return StringSubstitutor.replace(CANDIDATE_COUNT_URL_TEMPLATE, urlParams);
    }

    private String getCandidateCountResponse(String countUrl) {
        ResponseEntity<String> countResponseEntity = restTemplate.getForEntity(countUrl, String.class);
        return countResponseEntity.getBody();
    }

    @SneakyThrows // TODO: customize exception
    private int getCandidateCount2(String countJson) {
        JsonNode countTreeNode = objectMapper.readTree(countJson);
        JsonPointer countJsonPointer = JsonPointer.compile("/totalItems");
        JsonNode countNode = countTreeNode.at(countJsonPointer);
        return countNode.asInt();
    }

    private int getIndex(int candidateCount) {
        return random.nextInt(candidateCount);
    }

    private String getTitle(String category, int index) { // todo: страдает нэйминг
        String url = getBookUrl(category, index);
        String response = getBookResponse(url);
        return getTitle(response);
    }

    private String getBookUrl(String category, int index) {
        Map<String, String> urlParams = Map.of(
                "category", category,
                "index", String.valueOf(index)
        );
        return StringSubstitutor.replace(BOOK_URL_TEMPLATE, urlParams);
    }

    private String getBookResponse(String bookUrl) {
        ResponseEntity<String> bookResponseEntity = restTemplate.getForEntity(bookUrl, String.class);
        return bookResponseEntity.getBody();
    }

    @SneakyThrows // TODO: customize exception
    private String getTitle(String bookJson) {
        JsonNode bookTreeNode = objectMapper.readTree(bookJson);
        JsonPointer titleJsonPointer = JsonPointer.compile("/items/0/volumeInfo/title");
        JsonNode titleNode = bookTreeNode.at(titleJsonPointer);
        return titleNode.asText();
    }
}
