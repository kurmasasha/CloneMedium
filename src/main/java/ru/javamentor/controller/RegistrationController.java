package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String registrationUser(@Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        Role roleUser = roleService.getRoleByName("USER");
        user.setRole(roleUser);
        userService.addUser(user);
        model.addAttribute("regUser", user);
        return "activationInfo";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
            model.addAttribute("message", "User activated");
        } else {
            model.addAttribute("message", "Activation code not found!");
        }

        return "login";
    }

    @GetMapping("/resend")
    public String resend(Model model, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if(!(user.getActivationCode() == null)) {
            userService.resendActivationCode(user);
            //model.addAttribute("message", "Письмо отправлено");
        } else {
            model.addAttribute("message", "Email уже подтвержден!");
        }
        return "login";
    }
}
