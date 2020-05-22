package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Topic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TopicDAOImpl implements TopicDAO{

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
}
