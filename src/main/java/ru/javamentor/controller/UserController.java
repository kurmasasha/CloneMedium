package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;
import ru.javamentor.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", user);
        return "userPage";
    }

    @PostMapping("/user/edit_profile")
    public String upgrade(@ModelAttribute("user") User user, Model model) {
        User userDB = userService.getUserById(user.getId());
        userDB.setRole(roleService.getRoleById(2L));
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
      //  userDB.setUsername(user.getUsername());
        if (!user.getPassword().equals("")) {
            userDB.setPassword(user.getPassword());
        }
        if (userService.updateUser(userDB)) {
            return "redirect:/user";
        } else {
            model.addAttribute("message", "invalidData");
        }
        return "redirect:/user";
    }


}
