package ru.javamentor.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.model.User;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class NotificationRestController {

    private static int counter = 0;
    private static int randomNbr = 0;

    /**
     *  Возвращает количество новых уведомлений для залогиненого юзера - "ЗАГЛУШКА - имитатор"
     * @param 'Principal User'
     * @return случайное число нотификаций
     */
    @GetMapping("/user/MyNotifsNbr")
    public ResponseEntity<Integer> getNumberOfNotificationsNbrDumper(@AuthenticationPrincipal User user) throws InterruptedException {
        switch (counter) {
            case 0:
                counter++;
                randomNbr = ThreadLocalRandom.current().nextInt(1, 12 + 1);
                Thread.sleep(5000);
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
            case  1:
                counter++;
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
            case 2:
                counter++;
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
            case 3:
                counter++;
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
            case 4:
                counter++;
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
            default:
                counter = 0;
                randomNbr = ThreadLocalRandom.current().nextInt(1, 12 + 1);
                return new ResponseEntity<>( randomNbr, HttpStatus.OK);
        }
    }
}
