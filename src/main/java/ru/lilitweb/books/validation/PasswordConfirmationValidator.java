package ru.lilitweb.books.validation;

import ru.lilitweb.books.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator
        implements ConstraintValidator<PasswordConfirmation, Object> {

    @Override
    public void initialize(PasswordConfirmation constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
