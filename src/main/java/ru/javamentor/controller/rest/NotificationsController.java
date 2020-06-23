package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.NotificationServiceImpl;

import java.security.Principal;
import java.util.List;

/**
 * Контроллер для уведомлений
 *
 * @version 1.0
 * @autor Java Mentor
 */
@RestController
@RequestMapping(value = "/api/notifications")
public class NotificationsController {

    private NotificationServiceImpl service;

    @Autowired
    public NotificationsController(NotificationServiceImpl service) {
        this.service = service;
    }

    /**
     * получение списка уведомлений текущего пользователя
     * @param user - текущий пользователь
     * @return - список уведомлений
     */
    @GetMapping("/user")
    public ResponseEntity<List<Notification>> getUserNotifications(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(service.getUserNotifications(user.getId()), HttpStatus.OK);
    }

    /**
     * получение числа уведомлений тукущего пользователя
     * @param user - текущий пользователь
     * @return - число уведомлений
     */
    @GetMapping("/user-count")
    public ResponseEntity<Integer> getUserNotificationsCount(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(service.getNotificationsOfUserCount(user.getId()), HttpStatus.OK);
    }
}
