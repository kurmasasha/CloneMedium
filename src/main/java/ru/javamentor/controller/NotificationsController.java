package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Notification;
import ru.javamentor.service.NotificationServiceImpl;

import java.util.List;

@Controller
@RequestMapping(value = "/notifications")
public class NotificationsController {

    private NotificationServiceImpl service;

    @Autowired
    public NotificationsController(NotificationServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Notification>> showNotifications() {
        return new ResponseEntity<>(service.getAllNotes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> showNotificationById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Notification> showNotificationByTitle(@PathVariable("title") String title) {
        return new ResponseEntity<>(service.getByTitle(title), HttpStatus.OK);
    }

    @PostMapping("/addNewNotification")
    public ResponseEntity<Notification> addNotification(Notification notification){
        if (notification == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addNotification(notification);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Notification> updateNotification(Notification notification){
        if (service.updateNotification(notification)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id){
        Notification notification = service.getById(id);
        if(notification != null && service.deleteNotification(notification)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
