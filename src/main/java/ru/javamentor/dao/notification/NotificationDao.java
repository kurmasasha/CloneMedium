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

    List<Notification> getAllNotes();

    Notification getOne(Long id);

    Notification getByTitle(String title);

    void updateNotification(Notification notification);

    void addNotification(Notification notification);

    void deleteNotification(Notification notification);

    /**
     * метод для получения списка уведомлений для залогиненого автора / пользователя ( по user.id )
     *
     * @param userId - id автора / пользователя
     * @return список нотификаций для залогиненого автора / пользователя
     */
    List<Notification> getAllNotificationsByUserId(Long userId);

    /**
     * метод для получения числа уведомлений для залогиненого автора / пользователя ( по user.id )
     *
     * @param userId - id автора / пользователя
     * @return число нотификаций для залогиненого автора / пользователя
     */
    int getNumberOfNotificationsByUserId(Long userId);

    /**
     * метод для удаления уведомления
     *
     * @param id - уникальный id уведомления
     * @return void
     */
    void removeNotificationById(Long id);
}
