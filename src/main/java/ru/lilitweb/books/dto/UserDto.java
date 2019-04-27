package ru.lilitweb.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lilitweb.books.validation.PasswordConfirmation;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordConfirmation(message = "{userDto.error.password.confirmaion.incorrect}")
public class UserDto {
    @Size(min = 3, max=50, message = "{userDto.error.fullname.incorrect}")
    private String fullname;

    @Size(min = 3, max=50, message = "{userDto.error.password.incorrect}")
    private String password;

    @Size(min = 3, max=50, message = "{userDto.error.password.incorrect}")
    private String passwordConfirmation;

    @Email(message = "{userDto.error.email.incorrect}")
    private String email;
}
