package ru.javamentor.service;

import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;

public interface TopicService {

    boolean addTopic(String title, String content);

    Topic getTopicById(Long id);

    Topic getTopicByTitle(String title);

    boolean updateTopic(Topic topic);

    boolean removeTopicById(Long id);

    List<Topic> getAllTopicsByUserId(Long userId);

    List<Topic> getTotalListOfTopics();

    List<User> getAllUsersByTopicId(Long topicId);

    List<Topic> getAllTopicsOfAuthenticatedUser();

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsByHashtag(String value);

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     * @param userId - id пользователя
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value);

    /**
     *
     * @return список всех отмодерированных топиков
     */
    List<Topic> getAllModeratedTopics();
}
