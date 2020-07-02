package ru.javamentor.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javamentor.model.Notification;

import java.util.List;

/**
 * Интерфейс для работы с уведомлениями
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface NotificationService {
    /**
     * метод для получения всех уведомлений
     *
     * @return List уведомлений
     */
    List<Notification> getAllNotes();

    /**
     * метод для получения уведомления по уникальному Id
     *
     * @return Notification - объект уведомления
     */
    Notification getById(Long id);

    /**
     * метод для получения уведомления по названию
     *
     * @param title - наименование уведомления
     * @return Notification - объект уведомления
     */
    Notification getByTitle(String title);

    /**
     * метод для обновления уведомления
     *
     * @param notification - объект обновленнного уведомления
     * @return boolean - удалось обновить уведомление или нет
     */
    boolean updateNotification(Notification notification);

    /**
     * метод для добавления уведомления
     *
     * @param notification - объект добавляемого уведомления
     * @return boolean - удалось добавить уведомление или нет
     */
    boolean addNotification(Notification notification);

    /**
     * метод для удаления уведомления
     *
     * @param notification - объект удаляемого уведомления
     * @return boolean - удалось удалить уведомление или нет
     */
    boolean deleteNotification(Notification notification);


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
     * @return число уведомлений нотификаций для залогиненого автора / пользователя
     */
    int getNumberOfNotificationsByUserId(Long userId);

    /**
     * метод для удаления уведомления
     *
     * @param id - уникальный id уведомления
     * @return boolean - удалость удалить уведомление или нет
     */
    boolean removeNotificationById(Long id);

}



