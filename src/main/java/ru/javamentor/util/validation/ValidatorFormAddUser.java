package ru.javamentor.util.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javamentor.model.User;

import java.util.regex.Pattern;

/**
 * Класс отвечающий за валидацию формы добавления пользователя
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Component
public class ValidatorFormAddUser extends UserValidator {
    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        validEmail(user, errors);
        validPassword(user, errors);

        Pattern pattern = Pattern.compile("^[A-zА-яЁё]+$");
        validFirstName(user, errors, pattern);
        validLastName(user, errors, pattern);
    }
}
