package ru.javamentor.util.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javamentor.model.User;

import java.util.regex.Pattern;

/**
 * Класс отвечающий за валидацию формы редактирования пользователя
 *
 * @version 1.0
 * @author Java Mentor
 */
@Component
public class ValidatorFormEditUser extends UserValidator {

    /**
     * Проверка на валидность юзера
     */
    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        validPassword(user, errors);

        Pattern pattern = Pattern.compile("^[A-zА-яЁё]+$");
        validFirstName(user, errors, pattern);
        validLastName(user, errors, pattern);

        Pattern patternForPhoneNumber = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        validPhoneNumber(user, errors, patternForPhoneNumber);
    }
}
