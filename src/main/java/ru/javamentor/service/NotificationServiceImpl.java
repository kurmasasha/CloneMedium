package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javamentor.dao.NotificationsDao;
import ru.javamentor.model.Notification;

import java.util.List;
/**
 * Реализация интерфейса NotificationService
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationsDao notificationsDao;

    @Autowired
    public NotificationServiceImpl(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        List<Notification> notifications = notificationsDao.getNotificationsOfUser(userId);
        log.info("getUserNotifications: {} notifications", notifications.size());
        return notifications;
    }

    /**
     * получение числа уведомлений пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    public int getNotificationsOfUserCount(Long userId) {
        int result = notificationsDao.getNotificationsOfUserCount(userId);
        log.info("getNotificationsOfUserCount: {} notifications", result);
        return result;
    }
}
