package ru.javamentor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javamentor.model.Notification;

import java.util.List;

/**
 * Интерфейс для доступа к нотификациям из базы
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface NotificationsDao {
    /**
     * получение уведомлений для пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    List<Notification> getNotificationsOfUser(Long userId);

    /**
     * получение числа уведомлений пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    int getNotificationsOfUserCount(Long userId);
}
