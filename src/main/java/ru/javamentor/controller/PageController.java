package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;
import ru.javamentor.util.validation.ValidatorFormEditUser;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Контроллер возвращающий для показа html страниц
 * @autor Java Mentor
 * @version 1.0
 */
@Controller
public class PageController {

    private final UserService userService;
    public final TopicService topicService;
    private final ValidatorFormEditUser validatorFormEditUser;

    @Autowired
    public PageController(UserService userService, TopicService topicService, ValidatorFormEditUser validatorFormEditUser) {
        this.userService = userService;
        this.topicService = topicService;
        this.validatorFormEditUser = validatorFormEditUser;
    }

    /**
     * метод для страницы логина
     *
     * @param message - сообщение для вида
     * @param warning - сообщение предупреждения для вида
     * @param model   - объект для взаимодействия с видом
     * @return страницу логина
     */
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
    /**
     * метод для вида главной страницы
     * @return главную страницу
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    /**
     * метод для страницы всех топиков
     * @return страницу для показа всех топиков
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String allTopicsPage() {
        return "all_topics_page";
    }

    /**
     * метод для страницы определенного топика
     * @param id - id топика
     * @param model - объект для взаимодействия с видом
     * @return страницу для отображения топика
     */
    @GetMapping("/topic/{id}")
    public String topicPage(@PathVariable Long id, Model model) {
        model.addAttribute("topicId", id);
        return "topic";
    }
    /**
     * метод для страницы всех юзеров для админа
     * @return админскую страницу для отображения всех юзеров
     */
    @GetMapping("/admin/allUsers")
    public String adminAllUsersPage() {
        return "admin-all_users";
    }
    /**
     * метод для страницы неотмодерированных топиков для админа
     * @return страницу для отображения неотмодерированных топиков для админа
     */
    @GetMapping("/admin/moderate")
    public String adminModeratePage() {
        return "admin-moderate";
    }

    /**
     * метод для админской странцы редактировани пользователя
     * @param id - id пользователя которого необходимо редактировать
     * @param model - объект для взаимодействия с видом
     * @return страницу для отображения формы редактирования юзера
     */
    @GetMapping("/admin/form_edit_user/{id}")
    public String adminShowFormEditUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "form_edit_user";
    }

    /**
     * метод для применения операции обновления пользователя админом
     * @param user - валидный пользователь которого необходимо обновить
     * @param bindingResult - объект для распознования ошибок валидации
     * @return страницу для отображения формы редактирования юзера либо на страницу всех юзеров в случае успешного обеновления
     */
    @PostMapping("/admin/user/update")
    public String adminUserUpdate(@Valid User user, BindingResult bindingResult) {
        validatorFormEditUser.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "form_edit_user";
        }
        User userFromBD = userService.getUserById(user.getId());
        userFromBD.setFirstName(user.getFirstName());
        userFromBD.setLastName(user.getLastName());
        if (!user.getPassword().equals("")) {
            userFromBD.setPassword(user.getPassword());
        }
        userService.updateUser(userFromBD);
        return "redirect:/admin/allUsers";
    }
}

