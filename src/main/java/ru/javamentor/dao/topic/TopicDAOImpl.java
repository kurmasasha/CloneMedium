package ru.javamentor.dao.topic;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Hashtag;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Класс для доступа к топикам из базы с помощью Hibernate
 *
 * @version 1.0
 * @author Java Mentor
 */
@Repository
public class TopicDAOImpl implements TopicDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * метод для добавления топика в базу
     *
     * @param topic - объект представляющий топик
     */
    @Override
    public void addTopic(Topic topic) {
        entityManager.persist(topic);
    }

    /**
     * метод для получения топика по id
     *
     * @param id - уникальный id топика
     * @return Topic - объект представляющий топик
     */
    @Override
    public Topic getTopicById(Long id) {
        return entityManager.find(Topic.class, id);
    }

    // TODO Не рабочий метод!
    /**
     * метод для получения роли по заголовку/наименованию
     *
     * @param title - наименование топика
     * @return Topic - объект представляющий топик
     */
    @Override
    public Topic getTopicByTitle(String title) {
        return entityManager.find(Topic.class, title);
    }

    /**
     * метод для обновления топика
     *
     * @param topic - объект обновленного топика
     */
    @Override
    public void updateTopic(Topic topic) {
        entityManager.merge(topic);
    }

    /**
     * метод для удаления топика
     *
     * @param id - уникальный id топика
     */
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

    /**
     * метод для получения всех топиков конкретного пользователя
     *
     * @param userId - уникальный id пользователя
     * @return List топиков принадлежащих данному пользователю
     */
    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        return entityManager.createQuery(
                "SELECT t FROM Topic t " +
                        "LEFT JOIN FETCH t.authors a " +
                        "LEFT JOIN FETCH t.hashtags h " +
                        "LEFT JOIN FETCH a.role r  " +
                        "LEFT JOIN FETCH t.themes th " +
                        "WHERE a.id = :userId " +
                        "GROUP BY t.id " +
                        "ORDER BY t.dateCreated  DESC",
                Topic.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * метод для получения всех топиков из базы
     *
     * @return List топиков
     */
    @Override
    public List<Topic> getTotalListOfTopics() {
        return entityManager.createQuery(
                "SELECT t FROM Topic t " +
                        "LEFT JOIN FETCH t.hashtags h " +
                        "LEFT JOIN FETCH t.authors a " +
                        "LEFT JOIN FETCH a.role r " +
                        "LEFT JOIN FETCH t.themes th " +
                        "GROUP BY t.id " +
                        "ORDER BY t.dateCreated  DESC",
                Topic.class)
                .getResultList();
    }

    // TODO Не рабочий метод!
    public List<User> getAllUsersByTopicId(Long topicId) {
        return entityManager.createQuery("SELECT u FROM Topic t LEFT JOIN FETCH t.authors u WHERE t.id = :topicId GROUP BY u.id", User.class).setParameter("topicId", topicId).getResultList();
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsByHashtag(String value) {
        return entityManager
                .createQuery(
                        "SELECT t FROM Topic t " +
                                "LEFT JOIN FETCH t.authors a " +
                                "LEFT JOIN FETCH t.hashtags h " +
                                "LEFT JOIN FETCH a.role r " +
                                "LEFT JOIN FETCH t.themes th " +
                                "WHERE h.name = :value " +
                                "GROUP BY t.id " +
                                "ORDER BY t.dateCreated  DESC",
                        Topic.class)
                .setParameter("value", value)
                .getResultList();
    }

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     *
     * @param userId - id пользователя
     * @param value  - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value) {
        return entityManager
                .createQuery(
                        "SELECT t FROM Topic t " +
                                "LEFT JOIN FETCH t.authors a " +
                                "LEFT JOIN FETCH t.hashtags h " +
                                "LEFT JOIN FETCH a.role r " +
                                "LEFT JOIN FETCH t.themes th " +
                                "WHERE h.name = :value " +
                                "AND a.id = :userId " +
                                "GROUP BY t.id " +
                                "ORDER BY t.dateCreated  DESC",
                        Topic.class)
                .setParameter("value", value)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Поиск модерированных топиков.
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getModeratedTopics() {
        return entityManager
                .createQuery(
                        "SELECT t FROM Topic t " +
                                "LEFT JOIN FETCH t.authors a " +
                                "LEFT JOIN FETCH t.hashtags h " +
                                "LEFT JOIN FETCH a.role r " +
                                "LEFT JOIN FETCH t.themes th " +
                                "WHERE t.isModerate = true " +
                                "GROUP BY t.id " +
                                "ORDER BY t.dateCreated  DESC",
                        Topic.class)
                .getResultList();
    }

    /**
     * Поиск не модерированных топиков.
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopics() {
        return entityManager
                .createQuery("SELECT t FROM Topic t LEFT JOIN FETCH t.authors a LEFT JOIN FETCH t.hashtags h LEFT JOIN FETCH a.role r WHERE t.isModerate = false AND t.completed = true GROUP BY t.id ORDER BY t.dateCreated  DESC", Topic.class)

                .getResultList();
    }

    /**
     * Поиск не модерированных топиков.
     * Добавлена пагинация.
     *
     * @param page     - номер страницы
     * @param pageSize - размер страницы
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopicsPage(int page, int pageSize) {
        return entityManager
                .createQuery(
                        "SELECT t FROM Topic t " +
                                "LEFT JOIN FETCH t.authors a " +
                                "LEFT JOIN FETCH t.hashtags h " +
                                "LEFT JOIN FETCH a.role r " +
                                "WHERE t.isModerate = false " +
                                "AND t.completed = true " +
                                "GROUP BY t.id " +
                                "ORDER BY t.dateCreated DESC",
                        Topic.class)
                .setFirstResult(pageSize * (page - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * Определение числа  не модерированных топиков
     *
     * @return список топиков
     */
    @Override
    public Long getNotModeratedTopicsCount() {
        return entityManager
                .createQuery(
                        "SELECT COUNT(t.id) " +
                                "FROM Topic t " +
                                "WHERE t.isModerate = false " +
                                "AND t.completed = true",
                        Long.class)
                .getSingleResult();
    }

    /**
     * Поиск топиков по теме.
     * @param themesIds - id тем, по которым будем происходить поиск
     * @return - список топиков
     */
    @Override
    public List<Topic> getModeratedTopicsByTheme(Set<Long> themesIds) {
        return entityManager
                .createQuery(
                        "SELECT t FROM Topic t " +
                                "LEFT JOIN FETCH t.authors a " +
                                "LEFT JOIN FETCH t.hashtags h " +
                                "LEFT JOIN FETCH a.role r " +
                                "LEFT JOIN FETCH t.themes th " +
                                "WHERE th.id IN :value " +
                                "AND t.isModerate = true " +
                                "GROUP BY t.id " +
                                "ORDER BY t.dateCreated  DESC",
                        Topic.class)
                .setParameter("value", themesIds)
                .getResultList();
    }
}
