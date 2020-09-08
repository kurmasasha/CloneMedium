package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.user.UserService;

import java.util.List;

/**
 * Rest контроллер для уведомлений
 *
 * @version 1.0
 * @author Java Mentor
 */

@RestController
@RequestMapping("/api")
public class NotificationRestController {

    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationRestController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    /**
     *  Возвращает количество всех уведомлений для залогиненого юзера по его ID
     * @param 'Principal User'
     * @return DTO Нотификации для авторизованного юзера
     */
    @GetMapping("/user/MyNotifsNbr")
    public ResponseEntity<List<NotificationDto>> getNumberOfNotificationsNbrDumper(@AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(notificationService.getNotificationDtoListByNotifList(notificationService.getAllNotesById(currentUser.getId())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(notificationService.getNotificationDtoListByNotifList(notificationService.getAllNotesById(user.getId())), HttpStatus.OK);
        }
    }

    /**
     *  Метод для изменения уведомления.
     */
    @PostMapping("/user/notification/update")
    public ResponseEntity<String> updateComment(@RequestBody NotificationDto notification) {
        Notification currentNotification = notificationService.getById(notification.getId());
        currentNotification.setId(notification.getId());
        currentNotification.setTitle(notification.getTitle());
        currentNotification.setText(notification.getText());
        if (notificationService.updateNotification(currentNotification)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the comment because it doesn't exists.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Метод для удаления уведомления.
     *
     */

    @DeleteMapping("/user/notification/delete")
    public void deleteNotification(Long id) {
        notificationService.deleteNotification(notificationService.getById(id));
    }
}
