package com.alex788.bookworm_management.google_book_provider.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GoogleApiBook {

    private final String title;
    private final String category;
    private final int index;
    private final int candidateCount;
}
