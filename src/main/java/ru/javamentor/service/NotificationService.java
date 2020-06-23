package ru.javamentor.service;

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
     *  метод получения уведомлений по id пользователя
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    List<Notification> getUserNotifications(Long userId);

    /**
     * получение числа уведомлений пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    int getNotificationsOfUserCount(Long userId);
}
