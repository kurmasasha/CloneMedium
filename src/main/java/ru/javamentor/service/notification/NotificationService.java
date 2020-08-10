package ru.javamentor.service.notification;

import ru.javamentor.model.Notification;

import java.util.List;

/**
 * Интерфейс для работы с уведомлениями
 *
 * @version 1.0
 * @author Java Mentor
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

    boolean isExist(Long notifId);
}
