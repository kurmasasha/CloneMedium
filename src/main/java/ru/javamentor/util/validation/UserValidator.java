package ru.javamentor.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javamentor.dao.UserDAOImpl;
import ru.javamentor.model.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        User userFromBD = userDAO.getUserByEmail(user.getUsername());
        if (userFromBD != null) {
            errors.rejectValue("username", "", "this email already exists");
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            errors.rejectValue("matchingPassword", "", "passwords not match");
        }
    }
}
