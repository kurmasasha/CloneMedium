package ru.javamentor.service.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для работы с уведомлениями через WebSocket
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface WsNotificationService {

    /**
     * метод для отправки уведомления отдельному юзеру по WebSocket
     * @param user - User
     * @param notification - NotificationDto
     */
    void sendNotification(User user, NotificationDto notification);

    /**
     * метод для получения уведомлений отдельного юзера по WebSocket
     * @param user - User
     */
    void getNotifications(User user);
}
