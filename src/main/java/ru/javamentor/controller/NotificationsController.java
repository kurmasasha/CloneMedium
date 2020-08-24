package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.user.UserService;

import java.util.List;

/**
 * Контроллер для уведомлений
 *
 * @version 1.0
 * @author Java Mentor
 */
@Controller
@RequestMapping(value = "/notifications")
public class NotificationsController {

    private final NotificationService service;
    private final UserService userService;
    private final WsNotificationService wsNotificationService;

    @Autowired
    public NotificationsController(NotificationService service, UserService userService, WsNotificationService wsNotificationService) {
        this.service = service;
        this.userService = userService;
        this.wsNotificationService = wsNotificationService;
    }

    /**
     * метод получения всех уведомлений
     *
     * @return ResponseEntity, который содержит List уведомлений и статус ОК
     */
    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> showNotifications() {
        return new ResponseEntity<>(service.getNotificationDtoListByNotifList(service.getAllNotes()), HttpStatus.OK);
    }
    /**
     * метод получения всех уведомлений по аутентификации юзера
     *
     * @return ResponseEntity, который содержит List уведомлений и статус ОК
     */
    @GetMapping("/")
    public ResponseEntity<List<NotificationDto>> showNotificationsById(@AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(service.getNotificationDtoListByNotifList(service.getAllNotesById(user.getId())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(service.getNotificationDtoListByNotifList(service.getAllNotesById(user.getId())), HttpStatus.OK);
        }
    }

    /**
     * метод получения уведомления по названию
     *
     * @param title - название уведомления
     * @return ResponseEntity, который содержит увеломление и статус ОК
     */
    @GetMapping("/{title}")
    public ResponseEntity<Notification> showNotificationByTitle(@PathVariable("title") String title) {
        return new ResponseEntity<>(service.getByTitle(title), HttpStatus.OK);
    }

    /**
     * метод добаления нового уведомления
     *
     * @param notification - уведомление которое необходимо добавить
     * @return ResponseEntity, который содержит увеломление и статус CREATED или BAD REQUEST если не удалось добавить
     */
    @PostMapping("/addNewNotification")
    public ResponseEntity<Notification> addNotification(Notification notification) {
        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addNotification(notification);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    /**
     * метод обновления нового уведомления
     *
     * @param notification - уведомление которое необходимо обновить
     * @return ResponseEntity, который содержит увеломление и статус CREATED или BAD REQUEST если не удалось обновить
     */
    @PostMapping("/update")
    public ResponseEntity<Notification> updateNotification(Notification notification) {
        if (service.updateNotification(notification)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * метод удаления уведомления
     *
     * @param id - id уведомлениыя которое необходимо удалить
     * @return ResponseEntity, который содержит статус ОК или BAD REQUEST если не удалось удалить
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
        Notification notification = service.getById(id);
        if (notification != null && service.deleteNotification(notification)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/from-db", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> fromDb (@AuthenticationPrincipal User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getUserByEmail(auth.getName());
        wsNotificationService.getNotifications(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
