package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.javamentor.model.Comment;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;
import ru.javamentor.service.theme.ThemeService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;
import ru.javamentor.util.validation.ValidatorFormEditUser;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер возвращающий для показа html страниц
 *
 * @author Java Mentor
 * @version 1.0
 */
@Controller
public class PageController {

    private final UserService userService;
    private final ThemeService themeService;
    public final TopicService topicService;
    private final CommentService commentService;
    private final ValidatorFormEditUser validatorFormEditUser;

    @Autowired
    public PageController(UserService userService, ThemeService themeService, TopicService topicService, CommentService commentService, ValidatorFormEditUser validatorFormEditUser) {
        this.userService = userService;
        this.themeService = themeService;
        this.topicService = topicService;
        this.commentService = commentService;
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
     *
     * @return главную страницу
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    /**
     * метод для страницы всех топиков
     *
     * @return страницу для показа всех топиков
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String allTopicsPage(Model model) {
        model.addAttribute("themes", themeService.getAllThemes());
        return "all_topics_page";
    }

    /**
     * метод для страницы определенного топика
     *
     * @param id    - id топика
     * @param model - объект для взаимодействия с видом
     * @return страницу для отображения топика
     */
    @GetMapping("/topic/{id}")
    public String topicPage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            user = userService.getUserByEmail(auth.getName());
        }
        Topic topic = topicService.getTopicById(id);

        if (topic == null) {
            model.addAttribute("error", "Статья не существует.");
            return "topic_error";

        } else if (!topic.isModerate()) {
            Long userId = (user != null) ? user.getId() : null;
            if (topic.getAuthors().stream().noneMatch(us -> us.getId().equals(userId))) {
                model.addAttribute("error", "Доступ к статье ограничен.");
                return "topic_error";
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("topicId", id);
        List<Comment> comments = commentService.getAllCommentsByTopicId(id);
        model.addAttribute("comments", comments);
        return "topic";

    }

    /**
     * метод для страницы всех юзеров для админа
     *
     * @return админскую страницу для отображения всех юзеров
     */
    @GetMapping("/admin/allUsers")
    public String adminAllUsersPage() {
        return "admin-all_users";
    }

    /**
     * метод для страницы тем для админа
     *
     * @return админскую страницу для отображения всех тем
     */
    @GetMapping("/admin/themes")
    public String adminAllThemesPage(Model model) {
        model.addAttribute("themes", themeService.getAllThemes());
        return "admin-themes";
    }

    /**
     * метод для страницы неотмодерированных топиков для админа
     *
     * @return страницу для отображения неотмодерированных топиков для админа
     */
    @GetMapping("/admin/moderate")
    public String adminModeratePage() {
        return "admin-moderate";
    }

    /**
     * метод для админской странцы редактировани пользователя
     *
     * @param id    - id пользователя которого необходимо редактировать
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
     *
     * @param user          - валидный пользователь которого необходимо обновить
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

    /**
     * метод для страницы всех топиков по хэштегу
     *
     * @return страницу для показа всех топиков
     */
    @GetMapping("/topic/find/tag/{tag}")
    public String getPageWithTopicsByHashTag(Model model) {
        model.addAttribute("themes", themeService.getAllThemes());
        return "all_topics_page";
    }

    /**
     * метод для страницы всех топиков по автору
     * @return страницу для показа всех топиков
     */
    @GetMapping("/topic/find/author/{authorId}")
    public String getPageWithTopicsByAuthor(Model model){
        model.addAttribute("themes", themeService.getAllThemes());
        return "all_topics_page";
    }
}

