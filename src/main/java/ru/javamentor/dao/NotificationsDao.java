package ru.javamentor.dao;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javamentor.model.Notification;

public interface NotificationsDao extends JpaRepository<Notification, Long> {

    @Query("SELECT notification from Notification notification WHERE notification.title=:title")
    Notification findByTitle(@Param("title") String title);
}
