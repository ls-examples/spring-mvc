package ru.lilitweb.books.dao;
import ru.lilitweb.books.domain.Genre;
import ru.lilitweb.books.domain.User;

import java.util.List;

public interface GenreDao {
    int count();
    void insert(Genre genre);
    void update(Genre genre);
    Genre getById(int id);

    List<Genre> getAll();

    void delete(int id);
}
