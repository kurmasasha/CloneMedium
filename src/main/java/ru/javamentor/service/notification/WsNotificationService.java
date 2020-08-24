package ru.javamentor.service.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;

import java.util.List;

public interface WsNotificationService {
    void sendNotification(User user, NotificationDto notification);
    List<NotificationDto> getNotifications(User user);
    void delNotification(Notification notification);
}
