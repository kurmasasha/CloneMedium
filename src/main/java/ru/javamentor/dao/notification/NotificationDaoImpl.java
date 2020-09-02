package ru.javamentor.dao.notification;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Notification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Реализация интерфейса NotificationDao
 *
 * @version 1.0
 * @author Java Mentor
 */

@Repository
public class NotificationDaoImpl implements NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Метод для получения всех уведомлений.
     */
    @Override
    public List<Notification> getAllNotes() {
       return entityManager.createQuery("SELECT n FROM Notification n", Notification.class).getResultList();
    }

    /**
     * Метод для получения всех уведомлений по id.
     */
    @Override
    public List<Notification> getAllNotesById(Long id) {
        return entityManager.createQuery("SELECT n FROM Notification n where user_id LIKE :id", Notification.class).setParameter("id", id).getResultList();
    }

    /**
     * Метод для получения уведомления по id.
     */
    @Override
    public Notification getOne(Long id) {
        return entityManager.find(Notification.class, id);
    }

    /**
     * Метод для получения уведомления по заголовку.
     */
    @Override
    public Notification getByTitle(String title) {
        return entityManager.find(Notification.class, title);
    }

    /**
     * Метод для обновления уведомления.
     */
    @Override
    public void updateNotification(Notification notification) {
        entityManager.merge(notification);
    }

    /**
     * Метод для добавления уведомления.
     */
    @Override
    public void addNotification(Notification notification) {
        entityManager.persist(notification);
    }

    /**
     * Метод для удаления уведомления.
     */
    @Override
    public void deleteNotification(Notification notification) {
        entityManager.remove(notification);
    }

    @Override
    public boolean isExist(Long notifId){
        Long count = entityManager.createQuery("SELECT COUNT(n.id) FROM Notification n WHERE n.id = :notifId", Long.class)
                .setParameter("notifId", notifId)
                .getSingleResult();
        return count>0;
    }


}
