package com.alex788.bookworm_management.reading_recommendation.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ReadingRecommendation {

    private final String bookTitle;
}
