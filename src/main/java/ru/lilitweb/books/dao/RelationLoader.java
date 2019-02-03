package ru.lilitweb.books.dao;

import java.util.List;

public interface RelationLoader<R> {
    List<R> getByIds(List<Integer> ids);
}
