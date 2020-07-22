package ru.javamentor.controller.rest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.MailSender;
import ru.javamentor.service.NotificationService;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;
import ru.javamentor.util.buffer.LikeBuffer;
import ru.javamentor.util.img.LoaderImages;
import ru.javamentor.util.validation.topic.TopicValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Rest контроллер для топиков
 *
 * @version 1.0
 * @author Java Mentor
 */
@Slf4j
@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class TopicRestControllers {

    @Value("${site.link}")
    private String link;

    private final TopicService topicService;
    private final UserService userService;
    private final LikeBuffer likeBuffer;
    private final MailSender mailSender;
    private final NotificationService notificationService;
    private final LoaderImages loaderImages;
    private final TopicValidator topicValidator;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public TopicRestControllers(TopicService topicService, UserService userService, LikeBuffer likeBuffer, MailSender mailSender, NotificationService notificationService, LoaderImages loaderImages, TopicValidator topicValidator) {
        this.topicService = topicService;
        this.userService = userService;
        this.likeBuffer = likeBuffer;
        this.mailSender = mailSender;
        this.notificationService = notificationService;
        this.loaderImages = loaderImages;
        this.topicValidator = topicValidator;
    }

    /**
     * метод получения всех отмодерированных топиков
     *
     * @return ResponseEntity, который содержит List топиков
     */
    @GetMapping("/free-user/moderatedTopicsList")
    public ResponseEntity<List<Topic>> getTotalTopics() {
        return new ResponseEntity<>(topicService.getModeratedTopics(), HttpStatus.OK);
    }

    /**
     * метод получения топиков по теме
     *
     * @return ResponseEntity, который содержит List топиков
     */
    @PostMapping("/free-user/getTopicsByThemes")
    public ResponseEntity<List<Topic>> getTopicsByTheme(@RequestParam(name = "themes", required = false) Set<Long> themesIds) {
        if (themesIds == null || themesIds.isEmpty()) {
            return new ResponseEntity<>(topicService.getModeratedTopics(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(topicService.getModeratedTopicsByThemes(themesIds), HttpStatus.OK);
        }
    }

    /**
     * метод получения всех неотмодерированных топиков
     *
     * @return ResponseEntity, который содержит List топиков
     */
    @GetMapping("/admin/notModeratedTopics")
    public ResponseEntity<List<Topic>> getNotModeratedTopics() {
        return new ResponseEntity<>(topicService.getNotModeratedTopics(), HttpStatus.OK);
    }

    /**
     * метод для получения одной страницы неотмодерированных топиков
     *
     * @return ResponseEntity, который содержит List неотмодерированных топиков
     */
    //TODO пока жестко задаю количество записей на странице
    @GetMapping("/admin/notModeratedTopicsPage/{page}")
    public ResponseEntity<List<Topic>> getNotModeratedTopicsPage(@PathVariable(value = "page") Integer page) {
        return new ResponseEntity<>(topicService.getNotModeratedTopicsPage(page, 5), HttpStatus.OK);
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
     * @return ResponseEntity, который содержит List топиков этого юзера
     */
    @GetMapping("/user/MyTopics")
    public ResponseEntity<List<Topic>> getAllTopicsOfAuthenticatedUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(topicService.getAllTopicsByUserId(currentUser.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(topicService.getAllTopicsByUserId(user.getId()), HttpStatus.OK);
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
     * @param topicData - содержимое топика
     * @param principal - хранит инфо об авторизованном пользователе
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае если топик пуст
     */
    @PostMapping("/user/topic/add")
    public ResponseEntity<Object> addTopic(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("completed") boolean completed,
            @RequestParam(required = false) MultipartFile file,
            Principal principal
            ) throws Exception {
        String message = "Что то пошло не так! Попробуйте снова";
        String resultFileName = "no-image.png";
        try {
            // проверка на пустоту title and content
            topicValidator.checkTitleAndContent(title, content);
            // если пользователь загрузил файл и валидные поля title, content загружаем картинку на сервер
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                topicValidator.checkFile(file);
                if (topicValidator.getError() == null) {
                    resultFileName = loaderImages.upload(file);
                }
            }
            if (topicValidator.getError() != null) {
                return new ResponseEntity<>(topicValidator.getError(), HttpStatus.BAD_REQUEST);
            }

            Set<User> users = new HashSet<>();
            users.add(userService.getUserByUsername(principal.getName()));
            Topic topic = topicService.addTopic(title, content, completed, resultFileName, users);

            if (topic != null) {
                return new ResponseEntity<>(topic, HttpStatus.OK);
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
     * @param topic - обновленный топика
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае неудачи
     */
    @SneakyThrows
    @PostMapping(value = "/user/topic/update", headers = {"content-type=multipart/mixed","content-type=multipart/form-data"})
    public ResponseEntity<Object> updateTopic(@RequestPart("json") @Valid Topic topic,
                                              @RequestPart(required = false) MultipartFile file,
                                              Principal principal) {
        log.debug("From updateTopic in Service the topic id is {} and comment is {}", topic.getId(), topic.getContent());
        String message = "Что то пошло не так! Попробуйте снова";
        String resultFileName = "no-image.png";

        // проверка на пустоту title and content
        topicValidator.checkTitleAndContent(topic.getTitle(), topic.getContent());
        // если пользователь загрузил файл и валидные поля title, content загружаем картинку на сервер
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            topicValidator.checkFile(file);
            if (topicValidator.getError() == null) {
                resultFileName = loaderImages.upload(file);
            }
        }
        if (topicValidator.getError() != null) {
            return new ResponseEntity<>(topicValidator.getError(), HttpStatus.BAD_REQUEST);
        }

        Set<User> users = new HashSet<>();
        users.add(userService.getUserByUsername(principal.getName()));
        topic.setAuthors(users);
        topic.setDateCreated(LocalDateTime.now());
        topic.setImg(resultFileName);
        if (topicService.updateTopic(topic)) {
            var top = topicService.getTopicById(topic.getId());
            return new ResponseEntity<>(top, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для уадления топика
     *
     * @param id - id топика который необходимо удалить
     * @return ResponseEntity, который содержит добавленный топик и статус ОК либо BAD REQUEST в случае неудачи
     */
    @DeleteMapping("/user/topic/delete/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        if (topicService.removeTopicById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param tag  - строковое представление хэштега
     * @param user - данные пользователя, отправившего запрос
     * @return список топиков
     */
    @GetMapping("/free-user/get-topics-of-user-by-hashtag/{tag}")
    public ResponseEntity<List<Topic>> getAllTopicsOfUserByHashtag(@PathVariable String tag, @AuthenticationPrincipal User user) {
        tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsOfUserByHashtag(user.getId(), tag);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param tag - строковое представление хэштега
     * @return список топиков
     */
    @GetMapping("/free-user/get-all-topics-by-hashtag/{tag}")
    public ResponseEntity<List<Topic>> getAllTopicsByHashtag(@PathVariable String tag) {
       // tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsByHashtag(tag);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    /**
     * Поиск топиков по автору.
     *
     * @param authorId - id автора топиков
     * @return список топиков данного автора
     */
    @GetMapping("/free-user/get-all-topics-by-author/{authorId}")
    public ResponseEntity<List<Topic>> getAllTopicsByHashtag(@PathVariable Long authorId) {
        List<Topic> topics = topicService.getAllTopicsByUserId(authorId);
        return new ResponseEntity<>(topics, HttpStatus.OK);
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
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete this topic, try again", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для получения неотмодерированного топика по id админом
     *
     * @param id  - id топика который необходимо получитьв ответе
     * @return ResponseEntity с необходимым топиком и ОК статус
     */
    @GetMapping("/admin/topic/{id}")
    public ResponseEntity<Topic> getNomoderatedTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }

    /**
     * метод для лайка топика
     *
     * @param topicId - id  топика который нужно лайкнуть
     * @param session - текущая сессия клиента
     * @return Увеличенное количество топиков либо ответ что лайк с текущей сессии запрещен
     */
    @GetMapping("/topic/addLike/{topicId}")
    public ResponseEntity<Topic> increaseLikeOfTopic(@PathVariable Long topicId, HttpSession session) {
        if (!likeBuffer.isLikedTopic(session.getId(), topicId)) {
            likeBuffer.addLike(session.getId(), topicId);
            Topic topic = topicService.increaseTopicLikes(topicId);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else if (likeBuffer.isLikedTopic(session.getId(), topicId)) {
            likeBuffer.deleteLike(session.getId(), topicId);
            Topic topic = topicService.decreaseTopicLikes(topicId);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
