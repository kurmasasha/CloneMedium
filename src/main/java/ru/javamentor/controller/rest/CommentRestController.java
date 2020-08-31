package ru.javamentor.controller.rest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;

import java.util.List;

/**
 * Rest контроллер для комментариев
 *
 * @version 1.0
 * @author Java Mentor
 */

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final UserService userService;
    private final CommentService commentService;
    private final TopicService topicService;
    private final WsNotificationService wsNotificationService;
    private final NotificationService notificationService;

    @Autowired
    public CommentRestController(UserService userService, 
                                 CommentService commentService, 
                                 TopicService topicService,
                                 WsNotificationService wsNotificationService, 
                                 NotificationService notificationService) {
        this.userService = userService;
        this.commentService = commentService;
        this.topicService = topicService;
        this.wsNotificationService = wsNotificationService;
        this.notificationService = notificationService;
    }

    /**
     *  Метод получение списка комментариев топика.
     */
    @GetMapping("/free-user/allCommentsOfTopic/{id}")
    public ResponseEntity<List<Comment>> getAllUsersByTopicId(@PathVariable(value = "id") Long topicId) {
        return new ResponseEntity<>(commentService.getAllCommentsByTopicId(topicId), HttpStatus.OK);
    }

    /**
     *  Метод получение комментария по id.
     */
    @GetMapping("/user/comment/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(comment,HttpStatus.OK);
        }
    }

    /**
     *  Метод добавление комментария.
     */
    @PostMapping("/user/comment/add")
    public ResponseEntity<Comment> addTopic(@RequestBody String data, @AuthenticationPrincipal User user) {
        JSONObject jsonObj = new JSONObject(data);
        Long topicId = Long.parseLong(jsonObj.getString("topicId"));
        String comment = jsonObj.getString("comment");
        Topic topic = topicService.getTopicById(topicId);
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Comment newComment = commentService.addComment(comment, userService.getUserByEmail(auth.getName()), topic);
            for (User u : topic.getAuthors()) {
                Notification notification = new Notification();
                notification.setTitle("Новый комментарий");
                notification.setText("Пользователь " + userService.getUserByEmail(auth.getName()) + " оставил новый комментарий в статье " + topic.getTitle() + " ");
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            if (newComment != null) {
                return new ResponseEntity<>(newComment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            Comment newComment = commentService.addComment(comment, user, topic);
            for (User u : topic.getAuthors()) {
                Notification notification = new Notification();
                notification.setTitle("Новый комментарий");
                notification.setText("Пользователь " + user.getUsername() + " оставил новый комментарий в статье " + topic.getTitle() + " ");
                notification.setUser(u);
                notificationService.addNotification(notification);
                wsNotificationService.sendNotification(u, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            }
            if (newComment != null) {
                return new ResponseEntity<>(newComment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     *  Метод изменения комментария.
     */
    @PutMapping("/user/comment/update")
    public ResponseEntity<String> updateComment(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        if (commentService.updateComment(comment, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the comment because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Метод удаления комментария юзером по id.
     */
    @DeleteMapping("/user/comment/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        if (commentService.removeCommentById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the comment because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Метод удаления комментария админом по id.
     */
    @DeleteMapping("/admin/comment/delete/{id}")
    public ResponseEntity<String> deleteCommentByAdmin(@PathVariable Long id) {
        if (commentService.removeCommentById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete this comment, try again", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Метод добавление лайка на комментарий.
     */
    @GetMapping("/comment/addLike/{commentId}")
    public ResponseEntity<Comment> putLikeToComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Comment comment = commentService.putLikeToComment(commentId, userService.getUserByEmail(auth.getName()));
            Notification notification = new Notification();
            notification.setTitle("Новое уведомление");
            notification.setText("Пользователю " + userService.getUserByEmail(auth.getName()) + " понравился ваш комментарий " + comment.getText() + " ");
            notification.setUser(userService.getUserByEmail(auth.getName()));
            notificationService.addNotification(notification);
            wsNotificationService.sendNotification(userService.getUserByEmail(auth.getName()), notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        Comment comment = commentService.putLikeToComment(commentId, user);
        Notification notification = new Notification();
        notification.setTitle("Новое уведомление");
        notification.setText("Пользователю " + user.getUsername() + " понравился ваш комментарий " + comment.getText() + " ");
        notification.setUser(user);
        notificationService.addNotification(notification);
        wsNotificationService.sendNotification(user, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     *  Метод добавление дизлайка на комментарий.
     */
    @GetMapping("/comment/addDislike/{commentId}")
    public ResponseEntity<Comment> putDislikeToComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Comment comment = commentService.putDislikeToComment(commentId, userService.getUserByEmail(auth.getName()));
            Notification notification = new Notification();
            notification.setTitle("Новое уведомление");
            notification.setText("Пользователю " + userService.getUserByEmail(auth.getName()) + " не понравился ваш комментарий " + comment.getText() + " ");
            notification.setUser(userService.getUserByEmail(auth.getName()));
            notificationService.addNotification(notification);
            wsNotificationService.sendNotification(userService.getUserByEmail(auth.getName()), notificationService.getNotificationDto(notificationService.getById(notification.getId())));
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        Comment comment = commentService.putDislikeToComment(commentId, user);
        Notification notification = new Notification();
        notification.setTitle("Новое уведомление");
        notification.setText("Пользователю " + user.getUsername() + " не понравился ваш комментарий " + comment.getText() + " ");
        notification.setUser(user);
        notificationService.addNotification(notification);
        wsNotificationService.sendNotification(user, notificationService.getNotificationDto(notificationService.getById(notification.getId())));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
