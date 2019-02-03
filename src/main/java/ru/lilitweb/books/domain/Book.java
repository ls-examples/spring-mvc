package ru.lilitweb.books.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ru.lilitweb.books.dao.Entity;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Book implements Entity {
    private int id;

    public Book(int id, String title, int year, String description, int authorId) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
        this.authorId = authorId;
    }

    @NonNull
    private String title;

    @NonNull
    private int year;

    @NonNull
    private String description;

    @NonNull
    private int authorId;

    private User author;
    private List<Genre> genres;
}
