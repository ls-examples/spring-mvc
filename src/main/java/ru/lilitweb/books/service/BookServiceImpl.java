package ru.lilitweb.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lilitweb.books.dao.BookDao;
import ru.lilitweb.books.dao.GenreDao;
import ru.lilitweb.books.dao.HasOneRelation;
import ru.lilitweb.books.dao.UserDao;
import ru.lilitweb.books.domain.Book;
import ru.lilitweb.books.domain.User;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private UserDao userDao;
    private GenreDao genreDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, UserDao userDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.genreDao = genreDao;
    }

    @Override
    public void add(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = bookDao.getAll();

        bookDao.loadAuthors(books, userDao);
        bookDao.loadGenres(books, genreDao);

        return books;
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
