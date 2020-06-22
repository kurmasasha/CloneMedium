package ru.javamentor.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class NotificationRestController {

    /**
     *  Возвращает количество новых уведомлений для залогиненого юзера - "ЗАГЛУШКА - имитатор"
     * @param 'Principal User'
     * @return случайное число нотификаций
     */
    @GetMapping("/user/MyNotifs")
    public ResponseEntity<List<Notification>> getNumberOfNotificationsDumper(@AuthenticationPrincipal User user) {
        ArrayList<Notification> rndNtfLst = new ArrayList<>();
        int randomNum = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        for (int i = 0; i < randomNum; i++) {
            rndNtfLst.add(new Notification((long) i, Integer.toString(i), Integer.toString(i)));
        }
        return new ResponseEntity<>( rndNtfLst, HttpStatus.OK);
    }

}
