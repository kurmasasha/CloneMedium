package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Hashtag;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

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
        Topic topic = getTopicById(id);
        if (topic != null) {
            entityManager.remove(topic);
            for (Hashtag tag : topic.getHashtags()) {
                Set<Topic> topics = tag.getTopics();
                if (topics.isEmpty()) {
                    entityManager.remove(tag);
                }
            }
        }
    }

    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        return entityManager.createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r  WHERE a.id = :userId GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class).setParameter("userId", userId).getResultList();
    }
    @Override

    public List<Topic> getTotalListOfTopics() {
        return entityManager.createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH t.authors a LEFT JOIN FETCH a.role r GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class).getResultList();
    }

    public List<User> getAllUsersByTopicId(Long topicId) {
        return entityManager.createQuery("SELECT u FROM Topic t LEFT JOIN FETCH t.authors u WHERE t.id = :topicId GROUP BY u.id", User.class).setParameter("topicId", topicId).getResultList();
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsByHashtag(String value) {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE h.name = :value GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class)
                        .setParameter("value", value)
                        .getResultList();
    }

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     * @param userId - id пользователя
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value) {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE h.name = :value AND a.id = :userId GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class)
                        .setParameter("value", value)
                        .setParameter("userId", userId)
                        .getResultList();
    }

    /**
     * Поиск модерированных топиков.
     * @return список топиков
     */
    @Override
    public List<Topic> getModeratedTopics() {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE t.isModerate = true GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class)
                .getResultList();
    }

    /**
     * Поиск не модерированных топиков.
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopics() {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE t.isModerate = false GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class)
                .getResultList();
    }

    /**
     * Поиск не модерированных топиков.
     * Добавлена пагинация.
     * @param page - номер страницы
     * @param pageSize - размер страницы
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopicsPage(int page, int pageSize) {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE t.isModerate = false GROUP BY t.id ORDER BY t.dateCreated DESC", Topic.class)
                .setFirstResult(pageSize * (page-1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * Определение числа  не модерированных топиков
     * @return
     */
    @Override
    public Long getNotModeratedTopicsCount() {
        return entityManager
                .createQuery("SELECT COUNT(t.id) FROM Topic t WHERE t.isModerate = false", Long.class)
                .getSingleResult();
    }
}
