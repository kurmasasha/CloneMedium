package ru.javamentor.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.javamentor.dto.NotificationDto;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.user.UserService;

import java.util.List;
/**
 * Реализация интерфейса WsNotificationService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Component
public class WsNotificationServiceImpl implements WsNotificationService {


    private SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WsNotificationServiceImpl(SimpMessagingTemplate messagingTemplate, UserService userService, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    /**
     * метод для отправки уведомления отдельному юзера по WebSocket
     *
     */
    @Override
    public void sendNotification(User user, NotificationDto notification) {
        messagingTemplate.convertAndSendToUser(user.getUsername(),"/queue/notify", notification);
    }

    /**
     * метод для получения уведомлений отдельного юзера по WebSocket
     *
     */
    @Override
    public void getNotifications(User user) {
        messagingTemplate.convertAndSendToUser( user.getUsername(),
                "/queue/notify",
                notificationService.getNotificationDtoListByNotifList(notificationService.getAllNotesById(user.getId())));
    }
}
