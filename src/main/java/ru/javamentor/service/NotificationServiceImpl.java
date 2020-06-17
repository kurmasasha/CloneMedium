package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javamentor.dao.NotificationsDao;
import ru.javamentor.model.Notification;

import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationsDao notificationsDao;

    @Autowired
    public NotificationServiceImpl(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }

    @Override
    public List<Notification> getAllNotes() {
        List<Notification> notifications = notificationsDao.findAll();
        log.info("findAll: {} notifications", notifications.size());
        return notifications;
    }

    @Override
    public Notification getById(Long id) {
        Notification notification = notificationsDao.getOne(id);
        log.info("getById: return notification by Id");
        return notification;
    }

    @Override
    public Notification getByTitle(String title) {
        Notification notification = notificationsDao.findByTitle(title);
        log.info("getById: return notification by title");
        return notification;
    }

    @Override
    public boolean updateNotification(Notification notification) {
        if (isExistNotes(notification)) {
            notificationsDao.saveAndFlush(notification);
            log.info("updateNotification: notification {title} was updated", notification.getTitle());
            return true;
        } else
            log.info("updateNotification: notification {title} not found", notification.getTitle());
        return false;
    }

    @Override
    public boolean addNotification(Notification notification) {
        if (!isExistNotes(notification)) {
            notificationsDao.save(notification);
            return true;
        } else return false;
    }

    @Override
    public boolean deleteNotification(Notification notification) {
        notificationsDao.delete(notification);
        log.info("deleteNotification: notification {title} was deleted", notification.getTitle());
        return true;
    }

    private boolean isExistNotes(Notification notification) {
        return notificationsDao.findByTitle(notification.getTitle()) != null;
    }
}
