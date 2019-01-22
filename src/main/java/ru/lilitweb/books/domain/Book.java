package ru.lilitweb.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;

    @NonNull
    private String title;

    @NonNull
    private int year;

    @NonNull
    private String description;

    @NonNull
    private int authorId;
}
