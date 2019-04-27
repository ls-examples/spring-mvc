package ru.lilitweb.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User{
    @Id
    private String id;

    @NonNull
    private String fullname;

    @NonNull
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String email;
    private String password;

    private boolean enabled;
    private Set<Authority> authorities;
}
