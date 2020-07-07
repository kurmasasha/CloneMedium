package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.NotificationService;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;
import ru.javamentor.util.buffer.LikeBuffer;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Rest контроллер для топиков
 *
 * @version 1.0
 * @author Java Mentor
 */
@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class TopicRestControllers {

    private final TopicService topicService;
    private final UserService userService;
    private final LikeBuffer likeBuffer;
    private final NotificationService notificationService;

    @Autowired
    public TopicRestControllers(TopicService topicService, UserService userService, LikeBuffer likeBuffer, NotificationService notificationService) {
        this.topicService = topicService;
        this.userService = userService;
        this.likeBuffer = likeBuffer;
        this.notificationService = notificationService;
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
        for (User user:
                topic.getAuthors()) {
            Notification notification = new Notification();
            notification.setTitle("Модерация");
            notification.setText("Ваша статья \"" + topic.getTitle() + "\" прошла модерацию и одобренна");
            notification.setUser(user);
            notificationService.addNotification(notification);
            userService.notifyAllSubscribersOfAuthor(user.getUsername(), "Новая статья",
                    "Автор " + user.getUsername() + " опубликовал новую статью " + topic.getTitle());
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
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topicData, Principal principal) {
        Set<User> users = new HashSet<>();
        User user = userService.getUserByUsername(principal.getName());
        users.add(user);
        Topic topic = topicService.addTopic(topicData.getTitle(), topicData.getContent(), topicData.isCompleted(), users);
        if (topic != null) {
            return new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsByHashtag(tag);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    /**
     * метод для удаления топика
     *
     * @param id  - id топика который необходимо удалить
     * @return ResponseEntity со статусом ОК если удаление прошло успешно , иначе BAD REQUEST
     */
    @DeleteMapping("/admin/topic/delete/{id}")
    public ResponseEntity<String> deleteTopicByAdmin(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        Set<User> users = topic.getAuthors();

        if (topicService.removeTopicById(id)) {
            for (User user:
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
        } else if(likeBuffer.isLikedTopic(session.getId(), topicId)) {
            likeBuffer.deleteLike(session.getId(), topicId);
            Topic topic = topicService.decreaseTopicLikes(topicId);
            return new ResponseEntity<>(topic, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
