package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javamentor.dao.NotificationDAO;
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

    private NotificationDAO notificationDAO;

    @Autowired
    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    /**
     * метод для получения всех уведомлений
     *
     * @return List уведомлений
     */
    @Override
    public List<Notification> getAllNotes() {
        List<Notification> notifications = notificationDAO.getAllNotes();
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
        Notification notification = notificationDAO.getOne(id);
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
        Notification notification = notificationDAO.getByTitle(title);
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
            notificationDAO.updateNotification(notification);
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
            notificationDAO.addNotification(notification);
        }
        return true;
    }

    /**
     * метод для удаления уведомления
     *
     * @param notification - объект удаляемого уведомления
     * @return boolean - удалось удалить уведомление или нет
     */
     @Override
     public boolean deleteNotification(Notification notification) {
        notificationDAO.deleteNotification(notification);
        log.info("deleteNotification: notification {title} was deleted", notification.getTitle());
        return true;
     }

    /**
     * метод для получения списка уведомлений для залогиненого автора / пользователя ( по user.id )
     *
     * @param userId - id автора / пользователя
     * @return список нотификаций для залогиненого автора / пользователя
     */
    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        List<Notification> ntfLst = notificationDAO.getAllNotificationsByUserId(userId);
        log.info("for user ID  = {userId} derived {ntfSize} notifications", userId, ntfLst.size());
        return ntfLst;
    }

    /**
     * метод для получения числа уведомлений для залогиненого автора / пользователя ( по user.id )
     *
     * @param userId - id автора / пользователя
     * @return число уведомлений нотификаций для залогиненого автора / пользователя
     */
    @Override
    public int getNumberOfNotificationsByUserId(Long userId) {
        int nbrNtfs = notificationDAO.getNumberOfNotificationsByUserId(userId);
        log.info("for user ID  = {userId} derived {ntfSize} notifications", userId, nbrNtfs);
        return nbrNtfs;
    }

}
