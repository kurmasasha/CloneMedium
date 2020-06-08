package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;
import ru.javamentor.service.UserService;
import ru.javamentor.util.validation.UserValidator;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;

    @GetMapping
    public String showFormRegistration(User user) {
        return "registration_form";
    }

    @PostMapping
    public String registrationUser(@Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        Role roleUser = roleService.getRoleByName("USER");
        user.setRole(roleUser);
        userService.addUser(user);
        return "redirect:/login";
    }
}
