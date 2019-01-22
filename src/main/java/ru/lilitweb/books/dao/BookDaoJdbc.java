package ru.lilitweb.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.lilitweb.books.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        jdbc = jdbcTemplate;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from book", new HashMap<>(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("year", book.getYear());
        params.put("description", book.getDescription());
        params.put("author_id", book.getAuthorId());
        jdbc.update("insert into book (id, title, year, description, author_id) values (:id, :title, :year, :description, :author_id)",
                params);
    }

    @Override
    public void update(Book book) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("year", book.getYear());
        params.put("description", book.getDescription());
        params.put("author_id", book.getAuthorId());
        jdbc.update("update book set title=:title, year=:year, description=:description, author_id=:author_id where id=:id", params);
    }

    @Override
    public Book getById(int id) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("select * from book where id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select * from book", new HashMap<String, Book>(), new BookMapper());
    }

    @Override
    public List<Book> getAllByGenres(int[] genres) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("genres", Arrays.stream(genres)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        return jdbc.query("select book.* from book inner join book_genre on book.id=book_genre.book_id where book_genre.genre_id in (:genres) group by book.id",
                params, new BookMapper());
    }

    @Override
    public List<Book> getAllByAuthorId(int authorId) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("author_id", authorId);

        return jdbc.query("select * from book where author_id=:author_id", params, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            int year = resultSet.getInt("year");
            int authorId = resultSet.getInt("author_id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            return new Book(id, title, year, description, authorId);
        }
    }
}
