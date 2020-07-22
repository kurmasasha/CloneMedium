package ru.javamentor.dao.notification;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NotificationDaoImpl implements NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Notification> getAllNotes() {
       return entityManager.createQuery("SELECT n FROM Notification n", Notification.class).getResultList();
    }

    @Override
    public Notification getOne(Long id) {
        return entityManager.find(Notification.class, id);
    }

    @Override
    public Notification getByTitle(String title) {
        return entityManager.find(Notification.class, title);
    }

    @Override
    public void updateNotification(Notification notification) {
        entityManager.merge(notification);
    }

    @Override
    public void addNotification(Notification notification) {
        entityManager.persist(notification);
    }

    @Override
    public void deleteNotification(Notification notification) {
        entityManager.remove(notification);
    }

    /**
     * метод для получения списка уведомлений для залогиненого автора / пользователя ( по user.id )
     *
     * @param userId - id автора / пользователя
     * @return список нотификаций для залогиненого автора / пользователя
     */
    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {

        return entityManager.createQuery("SELECT n FROM Notification n LEFT JOIN FETCH n.user u WHERE u.id = :userId", Notification.class).setParameter("userId", userId).getResultList();

    }

    @Override
    public int getNumberOfNotificationsByUserId(Long userId) {
        return entityManager.createQuery("SELECT n FROM Notification n LEFT JOIN FETCH n.user u WHERE u.id = :userId", Notification.class).setParameter("userId", userId).getResultList().size();
    }

    /**
     * метод для удаления топика
     *
     * @param id - уникальный id топика
     * @return void
     */
    @Override
    public void removeNotificationById(Long id) {
        Notification notification = getOne(id);
        if (notification != null) {
            entityManager.remove(notification);
        }
    }
}
