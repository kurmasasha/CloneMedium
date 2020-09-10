package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.javamentor.dto.TopicDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.mailSender.MailSender;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;
import ru.javamentor.util.img.LoaderImages;
import ru.javamentor.util.validation.topic.TopicValidator;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Rest контроллер для топиков
 *
 * @author Java Mentor
 * @version 1.0
 */
@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class TopicRestControllers {

    @Value("${site.link}")
    private String link;

    private final TopicService topicService;
    private final UserService userService;
    private final MailSender mailSender;
    private final NotificationService notificationService;
    private final LoaderImages loaderImages;
    private final TopicValidator topicValidator;
    private final WsNotificationService wsNotificationService;

    @Autowired
    public TopicRestControllers(TopicService topicService,
                                UserService userService,
                                MailSender mailSender,
                                NotificationService notificationService,
                                LoaderImages loaderImages,
                                TopicValidator topicValidator,
                                WsNotificationService wsNotificationService) {
        this.topicService = topicService;
        this.userService = userService;
        this.mailSender = mailSender;
        this.notificationService = notificationService;
        this.loaderImages = loaderImages;
        this.topicValidator = topicValidator;
        this.wsNotificationService = wsNotificationService;
    }

    /**
     * метод получения всех отмодерированных топиков
     *
     * @return ResponseEntity, который содержит List TopicDto
     */
    @GetMapping("/free-user/moderatedTopicsList")
    public ResponseEntity<List<TopicDto>> getTotalTopics() {
        return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getModeratedTopics()), HttpStatus.OK);
    }

    /**
     * метод получения топиков по теме
     *
     * @return ResponseEntity, который содержит List TopicDto
     */
    @PostMapping("/free-user/getTopicsByThemes")
    public ResponseEntity<List<TopicDto>> getTopicsByTheme(@RequestParam(name = "themes", required = false) Set<Long> themesIds) {
        if (themesIds == null || themesIds.isEmpty()) {
            return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getModeratedTopics()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getModeratedTopicsByThemes(themesIds)), HttpStatus.OK);
        }
    }

    /**
     * метод получения всех неотмодерированных топиков
     *
     * @return ResponseEntity, который содержит List TopicDto
     */
    @GetMapping("/admin/notModeratedTopics")
    public ResponseEntity<List<TopicDto>> getNotModeratedTopics() {
        return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getNotModeratedTopics()), HttpStatus.OK);
    }

    /**
     * метод для получения одной страницы неотмодерированных топиков
     *
     * @return ResponseEntity, который содержит List TopicDto неотмодерированных топиков
     */
    //TODO пока жестко задаю количество записей на странице
    @GetMapping("/admin/notModeratedTopicsPage/{page}")
    public ResponseEntity<List<TopicDto>> getNotModeratedTopicsPage(@PathVariable(value = "page") Integer page) {
        return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getNotModeratedTopicsPage(page, 5)), HttpStatus.OK);
    }

    /**
     * метод для подсчета неотмодерированных топиков
     *
     * @return ResponseEntity, который содержит количество неотмодерированных топиков и статус ОК
     */
    @GetMapping("/admin/notModeratedTopicsCount")
    public ResponseEntity<Long> getNotModeratedTopicsCount() {
        return new ResponseEntity<>(topicService.getNotModeratedTopicsCount(), HttpStatus.OK);
    }

    /**
     * метод для получения топиков конкретного пользователя
     *
     * @param user - объект авторизованого пользователя
     * @return ResponseEntity, который содержит List TopicDto этого юзера
     */
    @GetMapping("/user/MyTopics")
    public ResponseEntity<List<TopicDto>> getAllTopicsOfAuthenticatedUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getAllTopicsByUserId(currentUser.getId())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topicService.getAllTopicsByUserId(user.getId())), HttpStatus.OK);
        }
    }

    /**
     * метод для получения юзеров связанного с данным топиком
     *
     * @param topicId - уникальный id топика
     * @return ResponseEntity, который содержит List пользователей
     */
    @GetMapping("/user/allUsersByTopicId/{id}")
    public ResponseEntity<List<User>> getAllUsersByTopicId(@PathVariable(value = "id") Long topicId) {
        return new ResponseEntity<>(topicService.getAllUsersByTopicId(topicId), HttpStatus.OK);
    }

    /**
     * метод модерации топиков
     *
     * @param id - уникальный id топика
     * @return ResponseEntity, который содержит статус Ok
     */
    @PostMapping("/admin/topic/moderate/{id}")
    public ResponseEntity<Topic> isModerate(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        topic.setModerate(true);
        topicService.updateTopic(topic);
        //добавление оповещения авторов что топик одобрен
        for (User user :
                topic.getAuthors()) {
            Notification notification = new Notification();
            notification.setTitle("Модерация");
            notification.setText("Ваша статья \"" + topic.getTitle() + "\" прошла модерацию и одобренна");
            notification.setUser(user);
            notificationService.addNotification(notification);
            wsNotificationService.sendNotification(user, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * метод для получения топика по  id
     *
     * @param id - уникальный id топика
     * @return ResponseEntity, который содержит топик и статус ответа ОК
     */
    @GetMapping("/user/topic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }

    /**
     * метод для добавления топика
     *
     * @param principal - хранит инфо об авторизованном пользователе
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае если топик пуст
     */
    @PostMapping("/user/topic/add")
    public ResponseEntity<Object> addTopic(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("completed") boolean completed,
            @RequestParam(required = false) MultipartFile file,
            Principal principal) {
        String message = "Что то пошло не так! Попробуйте снова";

        try {
            // проверка на пустоту title and content
            topicValidator.checkTitleAndContent(title, content);
            // если пользователь загрузил файл и валидные поля title, content загружаем картинку на сервер
            String resultFileName = loaderImages.fileToImage(file);

            if (topicValidator.getError() != null) {
                return new ResponseEntity<>(topicValidator.getError(), HttpStatus.BAD_REQUEST);
            }

            Set<User> users = new HashSet<>();
            users.add(userService.getUserByUsername(principal.getName()));

            Topic topic = topicService.addTopic(title, content, completed, resultFileName, users);

            for (User user : topic.getAuthors()) {
                Notification notification = new Notification();

                notification.setTitle("Модерация");
                notification.setText("Ваша статья \"" + topic.getTitle() + "\" ожидает модерацию");
                notification.setUser(user);

                notificationService.addNotification(notification);

                wsNotificationService.sendNotification(user,
                        notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }

            if (topic != null) {
                return new ResponseEntity<>(new TopicDto(topic), HttpStatus.OK);
            }

            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // тут мы логируем исключение, а пользователю кинем сообщение
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для обновления топика
     *
     * @param topic - обновленный топик
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае неудачи
     */
    @PostMapping("/user/topic/update")
    public ResponseEntity<String> updateTopic(@RequestBody Topic topic) {
        if (topicService.updateTopic(topic)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для удаления топика
     *
     * @param topicId - id топика который необходимо удалить
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае неудачи
     */
    @DeleteMapping("/user/topic/delete/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId, @AuthenticationPrincipal User user) {
        if (user != null) {
            if (topicService.isAuthorOfTopic(user.getId(), topicId) || user.getRole().getAuthority().equals("ADMIN")) {
                if (topicService.removeTopicById(topicId)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("You can't delete the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param tag  - строковое представление хэштега
     * @param user - данные пользователя, отправившего запрос
     * @return список TopicDto
     */
    @GetMapping("/free-user/get-topics-of-user-by-hashtag/{tag}")
    public ResponseEntity<List<TopicDto>> getAllTopicsOfUserByHashtag(@PathVariable String tag, @AuthenticationPrincipal User user) {
        tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsOfUserByHashtag(user.getId(), tag);
        return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topics), HttpStatus.OK);
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param tag - строковое представление хэштега
     * @return список TopicDto
     */
    @GetMapping("/free-user/get-all-topics-by-hashtag/{tag}")
    public ResponseEntity<List<TopicDto>> getAllTopicsByHashtag(@PathVariable String tag) {
        // tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsByHashtag(tag);
        return new ResponseEntity<>(topicService.getTopicDtoListByTopicList(topics), HttpStatus.OK);
    }

    /**
     * метод для удаления топика
     *
     * @param id - id топика который необходимо удалить
     * @return ResponseEntity со статусом ОК если удаление прошло успешно , иначе BAD REQUEST
     */
    @DeleteMapping("/admin/topic/delete/{id}")
    public ResponseEntity<String> deleteTopicByAdmin(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        Set<User> users = topic.getAuthors();
        if (topicService.removeTopicById(id)) {
            for (User user :
                    users) {
                Notification notification = new Notification();
                notification.setTitle("Модерация");
                notification.setText("Ваша статья \"" + topic.getTitle() + "\" не прошла модерацию и удалена");
                notification.setUser(user);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(user, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete this topic, try again", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для получения неотмодерированного топика по id админом
     *
     * @param id - id топика который необходимо получитьв ответе
     * @return ResponseEntity с необходимым топиком и ОК статус
     */
    @GetMapping("/admin/topic/{id}")
    public ResponseEntity<Topic> getNomoderatedTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }


    /**
     * Метод для добавления лайка
     *
     * @param topicId - id топика
     * @param user    - пользователь добавляющий лайк
     * @return ResponseEntity с необходимым топиком и ОК статус
     */
    @GetMapping("/topic/addLike/{topicId}")
    public ResponseEntity<Topic> addLikeToTopic(@PathVariable Long topicId, @AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User userByEmail = userService.getUserByEmail(auth.getName());
            Topic topic = topicService.addLikeToTopic(topicId, userByEmail);
            Notification notification = new Notification();
            notification.setTitle("Новое уведомление");
            notification.setText("Ваша статья \"" + topic.getTitle() + "\" понравилась пользователю " + auth.getName());
            for (User u : topic.getAuthors()) {
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            Topic topic = topicService.addLikeToTopic(topicId, user);
            for (User u : topic.getAuthors()) {
                Notification notification = new Notification();
                notification.setTitle("Новое уведомление");
                notification.setText("Ваша статья \"" + topic.getTitle() + "\" понравилась пользователю " + user.getUsername());
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
    }

    /**
     * Метод для добавления дизлайка
     *
     * @param topicId - id топика
     * @param user    - пользователь добавляющий дизлайк
     * @return ResponseEntity с необходимым топиком и ОК статус
     */
    @GetMapping("/topic/addDislike/{topicId}")
    public ResponseEntity<Topic> addDislikeToTopic(@PathVariable Long topicId, @AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User userByEmail = userService.getUserByEmail(auth.getName());
            Topic topic = topicService.addDislikeToTopic(topicId, userByEmail);
            Notification notification = new Notification();
            notification.setTitle("Новое уведомление");
            notification.setText("Ваша статья \"" + topic.getTitle() + "\" не понравилась пользователю" + auth.getName());
            for (User u : topic.getAuthors()) {
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            Topic topic = topicService.addDislikeToTopic(topicId, user);
            Notification notification = new Notification();
            notification.setTitle("Новое уведомление");
            notification.setText("Ваша статья \"" + topic.getTitle() + "\" не понравилась пользователю " + user.getUsername());
            for (User u : topic.getAuthors()) {
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
    }

    /**
     * метод для удаления автора у топика
     *
     * @param idTopic - id топика у которого необходимо удалить автора
     * @param idAuthor - username автора топика которого необходимо удалить
     * @return ResponseEntity со статусом ОК если удаление прошло успешно , иначе BAD REQUEST
     */
    @DeleteMapping("/topic/{idTopic}/author/{idAuthor}")
    public ResponseEntity<String> deleteAuthorOfTopic(@PathVariable Long idTopic, @PathVariable Long idAuthor) {
        Topic topic = topicService.getTopicById(idTopic);
        Set<User> authors = topic.getAuthors();
        User author = userService.getUserById(idAuthor);
        if (authors.size() != 1) {
            authors.remove(author);
                Notification notification = new Notification("Уведомление:",
                        "Вы больше не являетесь соавтором статьи \"" + topic.getTitle() + "\"",
                        author);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(author,
                        notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete all authors", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для добалвения автора к топику
     *
     * @param id - id топика к которому добавляем автора
     * @param username - username автора которого добавляем
     * @return ResponseEntity со статусом ОК если добавление прошло успешно , иначе BAD REQUEST
     */
    @PostMapping("/topic/{id}/author")
    public ResponseEntity<String> addAuthorToTopic(@PathVariable Long id,
                                                   @RequestParam(required = false, value = "username") String username) {
        Topic topic = topicService.getTopicById(id);
        Set<User> authors = topic.getAuthors();
        User author = null;
        try {
            author = userService.getUserByUsername(username);
        } catch (RuntimeException e) {
        }
        if (author != null && !authors.contains(author)) {
            authors.add(author);
            Notification notification = new Notification("Уведомление:",
                    "Вы назначены соавтором статьи \"" + topic.getTitle() + "\"",
                    author);
            notificationService.addNotification(notification);
            wsNotificationService.sendNotification(author,
                    notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            return new ResponseEntity<>("" + author.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("This user is not exist", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для получения списка лучших статей
     * (до 5 штук)
     */
    @GetMapping("/admin/best/topics")
    public ResponseEntity<List<Topic>> getFiveTopicsByLikes() {
        return new ResponseEntity<>(topicService.bestFive(), HttpStatus.OK);
    }

}
