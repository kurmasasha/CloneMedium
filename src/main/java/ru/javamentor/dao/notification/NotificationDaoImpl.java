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
}
