package ru.javamentor.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javamentor.dao.UserDAOImpl;
import ru.javamentor.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDAOImpl userDAO;

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,}$";
    //private static final String PASSWORD_PATTERN = "/^([a-zа-яё]+|\\d+)$/i";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        // email
        if (!user.getUsername().equals("")) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(user.getUsername());
            if (!matcher.matches()) {
                errors.rejectValue("username", "", "адресс электронной почты не соответствует формату example@mail.com");
            }
            User userFromBD = userDAO.getUserByEmail(user.getUsername());
            if (userFromBD != null) {
                errors.rejectValue("username", "", "Адрес электронной почты уже зарегистророван");
            }
        }

        // password
        if (!user.getPassword().equals("")) {
            // попробавал проверь пароль на защищенность, но регуларка не срабатывает
//            pattern = Pattern.compile(PASSWORD_PATTERN);
//            matcher = pattern.matcher(user.getPassword());
//            if (!matcher.matches()) {
//                errors.rejectValue("password", "", "пароль должен содержать латинские символы и цифры");
//            }
            if (!user.getPassword().equals(user.getMatchingPassword())) {
                errors.rejectValue("matchingPassword", "", "введенные пароли не совпадают");
            }
        }

        // firstName lastName
        pattern = Pattern.compile("^[A-zА-яЁё]+$");
        matcher = pattern.matcher(user.getFirstName());
        if (!user.getFirstName().equals("")) {
            if (!matcher.matches()) {
                errors.rejectValue("firstName", "", "только буквы");
            }
        }
        if (!user.getLastName().equals("")) {
            if (!matcher.matches()) {
                errors.rejectValue("lastName", "", "только буквы");
            }
        }
    }
}
