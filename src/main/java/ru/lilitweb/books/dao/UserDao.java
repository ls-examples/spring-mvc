package ru.lilitweb.books.dao;

import ru.lilitweb.books.domain.User;

import java.util.List;
import java.util.stream.IntStream;

public interface UserDao extends RelationLoader<User> {
    int count();
    void insert(User user);
    void update(User user);
    User getById(int id);

    List<User> getAll();

    void delete(int id);

    List<User> getByIds(List<Integer> ids);
}
