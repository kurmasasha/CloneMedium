package ru.javamentor.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javamentor.dao.user.UserDAOImpl;
import ru.javamentor.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс отвечающий за проверку на валидность данных пользователя в системе
 *
 * @version 1.0
 * @author Java Mentor
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDAOImpl userDAO;

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,}$";

    /**
     * Проверка на принадлежность к классу
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Проверка на валидность юзера
     */
    @Override
    public void validate(Object o, Errors errors) {}

    /**
     * Проверка на валидность по email
     */
    protected void validEmail(User user, Errors errors) {
        if (!user.getUsername().equals("")) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(user.getUsername());
            if (!matcher.matches()) {
                errors.rejectValue("username", "", "адресс электронной почты не соответствует формату example@mail.com");
            }
            User userFromBD = userDAO.getUserByEmail(user.getUsername());
            if (userFromBD != null) {
                errors.rejectValue("username", "", "Адрес электронной почты уже зарегистрирован");
            }
        }
    }

    /**
     * Проверка на валидность по паролю
     */
    protected void validPassword(User user, Errors errors) {
        if (!user.getPassword().equals("")) {
            if (!user.getPassword().equals(user.getMatchingPassword())) {
                errors.rejectValue("matchingPassword", "", "введенные пароли не совпадают");
            }
        }
    }

    /**
     * Проверка на валидность по имени
     */
    protected void validFirstName(User user, Errors errors, Pattern pattern) {
        matcher = pattern.matcher(user.getFirstName());
        if (!user.getFirstName().equals("")) {
            if (!matcher.matches()) {
                errors.rejectValue("firstName", "", "только буквы");
            }
        }
    }

    /**
     * Проверка на валидность по фамилии
     */
    protected void validLastName(User user, Errors errors, Pattern pattern) {
        matcher = pattern.matcher(user.getLastName());
        if (!user.getLastName().equals("")) {
            if (!matcher.matches()) {
                errors.rejectValue("lastName", "", "только буквы");
            }
        }
    }

    /**
     * Проверка на валидность по номеру телефона
     * Ориентировано на российские мобильные + городские с кодом из 3 цифр (например, Москва).
     */
    protected void validPhoneNumber(User user, Errors errors, Pattern pattern) {
        matcher = pattern.matcher(user.getPhoneNumber());
        if (!user.getPhoneNumber().equals("")) {
            if (!matcher.matches()) {
                errors.rejectValue("phoneNumber", "", "только цифры и символы: {'+', '-', '(', ')', ' '}");
            }
        }
    }

}
