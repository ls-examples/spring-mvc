package ru.lilitweb.books.dao;

import ru.lilitweb.books.domain.Book;
import java.util.List;

public interface BookDao {
    int count();
    void insert(Book book);
    void update(Book book);
    Book getById(int id);

    List<Book> getAll();

    List<Book> getAllByGenres(int[] genres);
    List<Book> getAllByAuthorId(int authorId);

    void delete(int id);
}
