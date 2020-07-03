package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javamentor.dao.NotificationsDao;
import ru.javamentor.dao.NotificationsDaoImpl;
import ru.javamentor.model.Notification;

import java.util.List;
/**
 * Реализация интерфейса NotificationService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationsDao notificationsDao;

    @Autowired
    public NotificationServiceImpl(NotificationsDao notificationsDao) {
        this.notificationsDao = notificationsDao;
    }
    /**
     * метод для получения всех уведомлений
     *
     * @return List уведомлений
     */
    @Override
    public List<Notification> getAllNotes() {
        List<Notification> notifications = notificationsDao.getAllNotes();
        log.info("findAll: {} notifications", notifications.size());
        return notifications;
    }

    /**
     * метод для получения уведомления по уникальному Id
     *
     * @return Notification - объект уведомления
     */
    @Override
    public Notification getById(Long id) {
        Notification notification = notificationsDao.getOne(id);
        log.info("getById: return notification by Id");
        return notification;
    }
    /**
     * метод для получения уведомления по названию
     *
     * @param title - наименование уведомления
     * @return Notification - объект уведомления
     */
    @Override
    public Notification getByTitle(String title) {
        Notification notification = notificationsDao.getByTitle(title);
        log.info("getById: return notification by title");
        return notification;
    }
    /**
     * метод для обновления уведомления
     *
     * @param notification - объект обновленнного уведомления
     * @return boolean - удалось обновить уведомление или нет
     */
    @Override
    public boolean updateNotification(Notification notification) {
        if (notification != null) {
            notificationsDao.updateNotification(notification);
            log.info("updateNotification: notification {title} was updated", notification.getTitle());
            return true;
        } else
            log.info("updateNotification: notification {title} not found", notification.getTitle());
        return false;
    }

    /**
     * метод для добавления уведомления
     *
     * @param notification - объект добавляемого уведомления
     * @return boolean - удалось добавить уведомление или нет
     */
    @Override
    public boolean addNotification(Notification notification) {
        if (notification != null) {
            notificationsDao.addNotification(notification);
            return true;
        }
        return false;
    }
    /**
     * метод для удаления уведомления
     *
     * @param notification - объект удаляемого уведомления
     * @return boolean - удалось удалить уведомление или нет
     */
    @Override
    public boolean deleteNotification(Notification notification) {
        notificationsDao.deleteNotification(notification);
        log.info("deleteNotification: notification {title} was deleted", notification.getTitle());
        return true;
    }

}
