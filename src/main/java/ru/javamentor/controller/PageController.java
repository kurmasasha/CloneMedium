package ru.javamentor.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.javamentor.model.Comment;
import ru.javamentor.model.User;
import ru.javamentor.service.CommentService;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;
import ru.javamentor.util.validation.ValidatorFormEditUser;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PageController {

    private final UserService userService;
    public final TopicService topicService;
    private final CommentService commentService;
    private final ValidatorFormEditUser validatorFormEditUser;

    @Autowired
    public PageController(UserService userService, TopicService topicService, CommentService commentService, ValidatorFormEditUser validatorFormEditUser) {
        this.userService = userService;
        this.topicService = topicService;
        this.commentService = commentService;
        this.validatorFormEditUser = validatorFormEditUser;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@ModelAttribute("message") String message, @ModelAttribute("warning") String warning, Model model) {
        boolean flagMessage = false;
        boolean flagWarning = false;
        if (message != null && !message.equals("")) {
            flagMessage = true;
        }
        if (warning != null && !warning.equals("")) {
            flagWarning = true;
        }
        model.addAttribute("flagMes", flagMessage);
        model.addAttribute("flagWar", flagWarning);
        return "login";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    @RequestMapping(value = "/allTopics", method = RequestMethod.GET)
    public String allTopicsPage() {
        return "all_topics_page";
    }

    @GetMapping("/")
    public String indexPage() {
        return "root";
    }

    @GetMapping("/topic/{id}")
    public String topicPage(@PathVariable Long id, Model model) {
        model.addAttribute("topicId", id);
        List<Comment> comments = commentService.getAllCommentsByTopicId(id);
        model.addAttribute("comments", comments);
        return "topic";
    }

    @GetMapping("/admin/allUsers")
    public String adminAllUsersPage() {
        return "admin-all_users";
    }

    @GetMapping("/admin/moderate")
    public String adminModeratePage() {
        return "admin-moderate";
    }

    @GetMapping("/admin/form_edit_user/{id}")
    public String adminShowFormEditUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "form_edit_user";
    }

    @PostMapping("/admin/user/update")
    public String adminUserUpdate(@Valid User user, BindingResult bindingResult) {
        validatorFormEditUser.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "form_edit_user";
        }
        User userFromBD = userService.getUserById(user.getId());
        userFromBD.setFirstName(user.getFirstName());
        userFromBD.setLastName(user.getLastName());
        userFromBD.setPassword(user.getPassword());
        userService.updateUser(userFromBD);
        return "redirect:/admin/allUsers";
    }
}

