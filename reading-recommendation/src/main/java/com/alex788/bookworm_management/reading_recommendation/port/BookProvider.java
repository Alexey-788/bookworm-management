package com.alex788.bookworm_management.reading_recommendation.port;

public interface BookProvider {

    String getRandomBookByCategory(String category);
}
