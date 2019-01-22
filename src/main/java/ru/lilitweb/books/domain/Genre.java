package ru.lilitweb.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Genre {
    private int id;

    @NonNull
    private String name;
}
