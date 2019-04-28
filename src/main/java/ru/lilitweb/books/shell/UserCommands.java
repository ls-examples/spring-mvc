package ru.lilitweb.books.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.lilitweb.books.domain.User;
import ru.lilitweb.books.dto.UserDto;
import ru.lilitweb.books.service.UserService;

@ShellComponent
@ShellCommandGroup("User")
public class UserCommands {
    private UserService userService;

    @Autowired
    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod("Create admin")
    String adminCreate(@ShellOption String email, @ShellOption String password) {
        User user = userService.registerNewUserAccount(new UserDto(
                email,
                password,
                password,
                email));
        userService.addRoleAdmin(user);
        return user.getId();
    }
}
