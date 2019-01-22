package ru.lilitweb.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private int id;

    @NonNull
    private String fullName;
}
