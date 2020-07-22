package ru.javamentor.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Notification;

import ru.javamentor.model.User;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.user.UserService;


import java.util.List;

@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class NotificationRestController {

    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationRestController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    /**
     *  Возвращает количество новых уведомлений для залогиненого юзера - "ЗАГЛУШКА - имитатор"
     * @param 'Principal User'
     * @return случайное число нотификаций
     */
    @GetMapping("/user/MyNotifsNbr")
    public ResponseEntity<Integer> getNumberOfNotificationsNbrDumper(@AuthenticationPrincipal User user) throws InterruptedException {

        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(notificationService.getNumberOfNotificationsByUserId(currentUser.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(notificationService.getNumberOfNotificationsByUserId(user.getId()), HttpStatus.OK);
        }
    }

    /**
     * метод для получения уведомлений конкретного пользователя
     *
     * @param user - объект авторизованого пользователя
     * @return ResponseEntity, который содержит List уведомлений этого юзера
     */
    @GetMapping("/user/MyNotifs")
    public ResponseEntity<List<Notification>> getAllNotificationsOfAuthenticatedUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.getUserByEmail(auth.getName());
            return new ResponseEntity<>(notificationService.getAllNotificationsByUserId(currentUser.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(notificationService.getAllNotificationsByUserId(user.getId()), HttpStatus.OK);
        }
    }

    /**
     * метод для уадления уведомления
     *
     * @param id - id уведомления который необходимо удалить
     * @return ResponseEntity, который содержит удалённое уведомление и статус ОК либо BAD REQUEST в случае неудачи
     */
    @DeleteMapping("/user/notification/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        if (notificationService.removeNotificationById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the notification because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * метод для получения уведомления по id
     *
     * @param id - уникальный id уведомления
     * @return ResponseEntity, который содержит уведомление и статус ответа ОК
     */
    @GetMapping("/user/notification/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return new ResponseEntity<>( notificationService.getById(id), HttpStatus.OK);
    }



//    private static int counter = 0;
//    private static int randomNbr = 0;

    /**
     *  Возвращает количество новых уведомлений для залогиненого юзера - "ЗАГЛУШКА - имитатор"
     * @param 'Principal User'
     * @return случайное число нотификаций
     */
//    @GetMapping("/user/MyNotifsNbr")
//    public ResponseEntity<Integer> getNumberOfNotificationsNbrDumper(@AuthenticationPrincipal User user) throws InterruptedException {
//        switch (counter) {
//            case 0:
//                counter++;
//                randomNbr = ThreadLocalRandom.current().nextInt(1, 12 + 1);
//                Thread.sleep(5000);
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//            case  1:
//                counter++;
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//            case 2:
//                counter++;
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//            case 3:
//                counter++;
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//            case 4:
//                counter++;
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//            default:
//                counter = 0;
//                randomNbr = ThreadLocalRandom.current().nextInt(1, 12 + 1);
//                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
//        }
//    }
}
