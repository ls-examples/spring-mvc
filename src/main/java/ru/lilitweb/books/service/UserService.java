package ru.lilitweb.books.service;

import ru.lilitweb.books.domain.User;
import ru.lilitweb.books.dto.UserDto;

public interface UserService {
    User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
    void addRoleAdmin(User user);
}
