package ru.lilitweb.books.dao;
import ru.lilitweb.books.domain.Genre;

import java.util.List;

public interface GenreDao extends RelatedEntitiesLoader<Genre> {
    int count();
    void insert(Genre genre);
    void update(Genre genre);
    Genre getById(int id);

    List<Genre> getAll();
    List<Genre> getByIds(List<Integer> ids);

    void delete(int id);
}
