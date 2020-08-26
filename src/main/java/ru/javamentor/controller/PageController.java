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
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.passwordRecoveryToken.PasswordRecoveryTokenService;
import ru.javamentor.service.role.RoleService;
import ru.javamentor.service.theme.ThemeService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;
import ru.javamentor.util.validation.ValidatorFormEditUser;

import javax.servlet.http.HttpServletRequest;
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
    private final RoleService roleService;
    public final TopicService topicService;
    private final CommentService commentService;
    private final ValidatorFormEditUser validatorFormEditUser;
    private final PasswordRecoveryTokenService passwordRecoveryTokenService;

    @Autowired
    public PageController(UserService userService, 
                          ThemeService themeService, 
                          TopicService topicService, 
                          CommentService commentService,
                          ValidatorFormEditUser validatorFormEditUser, 
                          RoleService roleService, P
                          asswordRecoveryTokenService passwordRecoveryTokenService) {
        this.userService = userService;
        this.themeService = themeService;
        this.topicService = topicService;
        this.commentService = commentService;
        this.validatorFormEditUser = validatorFormEditUser;
        this.passwordRecoveryTokenService = passwordRecoveryTokenService;
        this.roleService = roleService;
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
    public String homePage(Model model, @AuthenticationPrincipal User user) {
        List<Topic> topics = topicService.getAllTopicsByUserId(user.getId());
        model.addAttribute("topics", topicService.getTopicDtoListByTopicList(topics));
        return "home";
    }

    /**
     * метод для страницы всех топиков
     *
     * @return страницу для показа всех топиков
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String allTopicsPage(Model model) {
        List<Topic> topics = topicService.getModeratedTopics();
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("topicList", topicService.getTopicDtoListByTopicList(topics));
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
            model.addAttribute("error", "Такой статьи не существует.");
            return "topic_error";

        } else if (!topic.isModerate()) {
            Long userId = (user != null) ? user.getId() : null;
            if (user == null || topic.getAuthors().stream().noneMatch(us -> us.getId().equals(userId)) && !user.getRole().getName().equals("ADMIN")) {
                model.addAttribute("error", "Вы не можете просматривать данную статью.");
                return "topic_error";
            }
        }
        List<Comment> comments = commentService.getAllCommentsByTopicId(id);

        model.addAttribute("topic", topic);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "topic";

    }

    /**
     * метод для страницы всех юзеров для админа
     *
     * @return админскую страницу для отображения всех юзеров
     */
    @GetMapping("/admin/allUsers")
    public String adminAllUsersPage(Model model, @AuthenticationPrincipal User auth) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("authUser", auth);
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
     * метод для страницы восстановления пароля
     *
     * @return страница опопвещения об отправке временного пароля
     */
    @GetMapping("/recoveryPass/**")
    //Не используется /recoveryPass/{token} потому что токен может содержать символ "/" - ошибка 404
    public String recoveryPassPage(HttpServletRequest request, Model model) {
        String token = request.getRequestURI().split(request.getContextPath() + "/recoveryPass/")[1];

        PasswordRecoveryToken passwordRecoveryToken = passwordRecoveryTokenService.getPasswordRecoveryTokenByToken(token);
        if (passwordRecoveryToken != null && passwordRecoveryTokenService.isValid(passwordRecoveryToken)) {
            User user = passwordRecoveryToken.getUser();
            String tempPass = passwordRecoveryTokenService.generateTempPass();
            user.setPassword(tempPass);
            userService.updateUser(user);
            passwordRecoveryTokenService.sendTempPass(user, tempPass);
            passwordRecoveryTokenService.deletePasswordRecoveryToken(passwordRecoveryToken);
            model.addAttribute("message", "Временный пароль отправлен на почту");
            return "password_recovery_result";
        }

        model.addAttribute("message", "Не удалось сбросить пароль");
        return "password_recovery_result";
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
    public String getPageWithTopicsByHashTag(Model model, @PathVariable String tag) {
        List<Topic> topics = topicService.getAllTopicsByHashtag(tag);
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("topicList", topicService.getTopicDtoListByTopicList(topics));
        return "all_topics_page";
    }

    /**
     * метод для страницы всех топиков по автору
     *
     * @param authorId - id автора топиков
     * @return страницу для показа всех топиков
     */
    @GetMapping("/topic/find/author/{authorId}")
    public String getPageWithTopicsByAuthor(Model model, @PathVariable Long authorId) {
        List<Topic> topics = topicService.getAllTopicsByUserId(authorId);
        model.addAttribute("themes", themeService.getAllThemes());
        model.addAttribute("topicList", topicService.getTopicDtoListByTopicList(topics));
        return "all_topics_page";
    }

    /**
     * метод для активации пользователя админом
     *
     * @param enableId - уникальный id пользователя которого необходимо активировать
     * @return редирект на список пользователей
     */
    @PostMapping("/admin/enable/")
    public String enableUser(@RequestParam Long enableId) {
        User user = userService.getUserById(enableId);
        user.setLockStatus(!(user.getLockStatus()));

        userService.updateUser(user);
        return "redirect:/admin/allUsers";
    }

    /**
     * метод для деактивации пользователя админом
     *
     * @param disableId - уникальный id пользователя которого необходимо отключить
     * @return редирект на список пользователей
     */
    @PostMapping("/admin/disable/")
    public String disableUser(@RequestParam Long disableId) {
        User user = userService.getUserById(disableId);
        user.setLockStatus(!(user.getLockStatus()));

        userService.updateUser(user);
        return "redirect:/admin/allUsers";
    }

}

