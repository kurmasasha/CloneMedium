package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TopicDAOImpl implements TopicDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addTopic(Topic topic) {
        entityManager.persist(topic);
    }

    @Override
    public Topic getTopicById(Long id) {
        return entityManager.find(Topic.class, id);
    }

    @Override
    public Topic getTopicByTitle(String title) {
        return entityManager.find(Topic.class, title);
    }

    @Override
    public void updateTopic(Topic topic) {
        entityManager.merge(topic);
    }

    @Override
    public void removeTopicById(Long id) {
        entityManager.remove(getTopicById(id));
    }

    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        return entityManager.createQuery("SELECT t FROM Topic t JOIN t.authors a  WHERE a.id = :userId", Topic.class).setParameter("userId", userId).getResultList();
    }

}
