package ru.javamentor.dao.topic;

import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для доступа к топикам из базы
 *
 * @version 1.0
 * @author Java Mentor
 */
public interface TopicDAO {
    /**
     * метод для добавления топика в базу
     *
     * @param topic - объект представляющий топик
     */
    void addTopic(Topic topic);

    /**
     * метод для получения топика по id
     *
     * @param id - уникальный id топика
     * @return Topic - объект представляющий топик
     */
    Topic getTopicById(Long id);

    /**
     * метод для получения роли по заголовку/наименованию
     *
     * @param title - наименование топика
     * @return Topic - объект представляющий топик
     */
    Topic getTopicByTitle(String title);

    /**
     * метод для обновления топика
     *
     * @param topic - объект обновленного топика
     */
    void updateTopic(Topic topic);

    /**
     * метод для удаления топика
     *
     * @param id - уникальный id топика
     */
    void removeTopicById(Long id);

    /**
     * метод для получения всех топиков конкретного пользователя
     *
     * @param userId - уникальный id пользователя
     * @return List топиков принадлежащих данному пользователю
     */
    List<Topic> getAllTopicsByUserId(Long userId);

    /**
     * метод для получения всех топиков из базы
     *
     * @return List топиков
     */
    List<Topic> getTotalListOfTopics();

    /**
     * метод для получения всех пользователей связанных с топиком
     *
     * @param topicId - уникальный id топика
     * @return List пользователей
     */
    List<User> getAllUsersByTopicId(Long topicId);

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsByHashtag(String value);

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     *
     * @param userId - id пользователя
     * @param value  - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value);

    /**
     * Поиск модерированных топиков.
     *
     * @return список топиков
     */
    List<Topic> getModeratedTopics();

    /**
     * Поиск не модерированных топиков.
     *
     * @return список топиков
     */
    List<Topic> getNotModeratedTopics();

    /**
     * Поиск не модерированных топиков.
     * Добавлена пагинация.
     *
     * @param page     - номер страницы
     * @param pageSize - размер страницы
     * @return список топиков
     */
    List<Topic> getNotModeratedTopicsPage(int page, int pageSize);

    /**
     * Определение числа  не модерированных топиков
     *
     * @return - число не модерированных топиков
     */
    Long getNotModeratedTopicsCount();

    /**
     * Поиск топиков по теме.
     * @param themesIds - id тем, по которым будем происходить поиск
     * @return список топиков
     */
    List<Topic> getModeratedTopicsByTheme(Set<Long> themesIds);

    boolean isExist(Long topicId);
}
