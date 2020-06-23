package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Notification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Класс для доступа к топикам из базы с помощью Hibernate
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Repository
public class NotificationDAOImpl implements NotificationsDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * получение уведомлений для пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    @Override
    public List<Notification> getNotificationsOfUser(Long userId) {
        return entityManager.createQuery(
                "SELECT n FROM Notification n " +
                        "LEFT JOIN FETCH n.users u " +
                        "LEFT JOIN FETCH u.role r " +
                        "WHERE u.id = :userId " +
                        "GROUP BY n.id", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * получение числа уведомлений пользователя по его id
     * @param userId - id пользователя
     * @return - список уведомлений
     */
    @Override
    public int getNotificationsOfUserCount(Long userId) {
        Integer n = entityManager
                .createQuery("SELECT u.notifications.size FROM User u WHERE u.id = 1", Integer.class)
                .getSingleResult();
        return n;
    }
}
