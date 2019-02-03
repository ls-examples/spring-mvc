package ru.lilitweb.books.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

@Builder
public class ManyToManyRelation<E extends Entity, R extends Entity> {
    private final ToIntFunction<E> foreignKeyGetter;
    private final RelationSetter<E, R> relationSetter;
    private final String table;
    private final String foreignKey;
    private final String otherKey;

    public void load(List<E> entities, NamedParameterJdbcTemplate jdbc, RelatedEntitiesLoader<R> relatedEntitiesLoader) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", entities.stream().
                mapToInt(Entity::getId).
                distinct().
                boxed().
                collect(Collectors.toList()));

        List<Row> rows = jdbc.query(
                "select * from :table where :foreignKey in (:ids)",
                params,
                new Mapper(foreignKey, otherKey));

        List<R> relatedEntities = relatedEntitiesLoader.getByIds(
                rows.stream().map(row -> row.otherKey).collect(Collectors.toList()));

        HashMap<Integer, Set<Integer>> foreignToOtherMap = new HashMap<>();
        rows.forEach(row -> {
            if (foreignToOtherMap.containsKey(row.foreignKey)) {
                foreignToOtherMap.get(row.foreignKey).add(row.otherKey);
                return;
            }

            HashSet<Integer> s = new HashSet<>();
            s.add(row.otherKey);
            foreignToOtherMap.put(row.foreignKey, s);
        });

        entities.forEach(e -> relationSetter.apply(e,
                relatedEntities.stream().
                        filter(r -> foreignToOtherMap.get(e.getId()).contains(r.getId())).
                        collect(Collectors.toList())));
    }

    @AllArgsConstructor
    @Data
    private static class Row {
        Integer foreignKey;
        Integer otherKey;
    }

    @AllArgsConstructor
    private static class Mapper implements RowMapper<Row> {
        private String foreignKey;
        private String otherKey;

        @Override
        public Row mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Row(
                    resultSet.getInt(this.foreignKey),
                    resultSet.getInt(this.otherKey)
            );
        }
    }

    @FunctionalInterface
    public interface RelationSetter<E, R> {
        void apply(E e, List<R> r);
    }

}
