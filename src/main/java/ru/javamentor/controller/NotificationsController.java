package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javamentor.model.Notification;
import ru.javamentor.service.NotificationServiceImpl;

import java.util.List;

/**
 * Контроллер для уведомлений
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Controller
@RequestMapping(value = "/notifications")
public class NotificationsController {

    private NotificationServiceImpl service;

    @Autowired
    public NotificationsController(NotificationServiceImpl service) {
        this.service = service;
    }

    /**
     * метод получения всех уведомлений
     *
     * @return ResponseEntity, который содержит List уведомлений и статус ОК
     */
    @GetMapping("/")
    public ResponseEntity<List<Notification>> showNotifications() {
        return new ResponseEntity<>(service.getAllNotes(), HttpStatus.OK);
    }

    /**
     * метод получения уведомления по уникальному ID
     *
     * @param id - уникальный id уведомления
     * @return ResponseEntity, который содержит увеломление и статус ОК
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notification> showNotificationById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
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
}
