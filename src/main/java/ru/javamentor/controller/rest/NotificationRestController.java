package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.user.UserService;

import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
     *  Возвращает количество новых уведомлений для залогиненого юзера - "ЗАГЛУШКА - имитатор"
     * @param 'Principal User'
     * @return случайное число нотификаций
     */
    @GetMapping("/user/MyNotifsNbr")
    public ResponseEntity<List<Notification>> getNumberOfNotificationsNbrDumper(@AuthenticationPrincipal User user) throws InterruptedException {
        return new ResponseEntity<>( notificationService.getAllNotes(), HttpStatus.OK);
    }
}
