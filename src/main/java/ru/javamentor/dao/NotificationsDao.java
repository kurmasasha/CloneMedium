package ru.javamentor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javamentor.model.Notification;

/**
 * Интерфейс для доступа к нотификациям из базы
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface NotificationsDao extends JpaRepository<Notification, Long> {
    /**
     * метод для получения уведомления по имени
     *
     * @param title - наименование уведомления
     * @return объект уведомления
     */
    @Query("SELECT notification from Notification notification WHERE notification.title=:title")
    Notification findByTitle(@Param("title") String title);
}
