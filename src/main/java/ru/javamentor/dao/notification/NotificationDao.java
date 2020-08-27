package ru.javamentor.dao.notification;


import ru.javamentor.model.Notification;

import java.util.List;

/**
 * Интерфейс для доступа к нотификациям из базы
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface NotificationDao {

    /**
     * Метод для получения всех уведомлений.
     */
    List<Notification> getAllNotes();

    /**
     * Метод для получения всех уведомлений по id.
     */
    List<Notification> getAllNotesById(Long id);

    /**
     * Метод для получения уведомления по id.
     */
    Notification getOne(Long id);

    /**
     * Метод для получения уведомления по заголовку.
     */
    Notification getByTitle(String title);

    /**
     * Метод для обновления уведомления.
     */
    void updateNotification(Notification notification);

    /**
     * Метод для добавления уведомления.
     */
    void addNotification(Notification notification);

    /**
     * Метод для удаления уведомления.
     */
    void deleteNotification(Notification notification);

    boolean isExist(Long notifId);
}
