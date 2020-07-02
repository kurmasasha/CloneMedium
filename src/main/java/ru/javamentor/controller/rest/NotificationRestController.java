package ru.javamentor.controller.rest;

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
import ru.javamentor.service.NotificationService;
import ru.javamentor.service.UserService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Rest контроллер для уведомлений
 *
 * @version 1.0
 * @autor Java Mentor
 */
@RestController
@RequestMapping(value = {"/api"}, produces = "application/json")
public class NotificationRestController {


    private final UserService userService;
    private final NotificationService notificationService;

    private static int counter = 0;
    private static int randomNbr = 0;

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
     * метод для получения топиков конкретного пользователя
     *
     * @param user - объект авторизованого пользователя
     * @return ResponseEntity, который содержит List топиков этого юзера
     */
//    @GetMapping("/user/MyTopics")
//    public ResponseEntity<List<Topic>> getAllTopicsOfAuthenticatedUser(@AuthenticationPrincipal User user) {
//        if (user == null) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            User currentUser = userService.getUserByEmail(auth.getName());
//            return new ResponseEntity<>(topicService.getAllTopicsByUserId(currentUser.getId()), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(topicService.getAllTopicsByUserId(user.getId()), HttpStatus.OK);
//        }
//    }

}
