package ru.javamentor.dao;

import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;

public interface TopicDAO {

    void addTopic(Topic topic);

    Topic getTopicById(Long id);

    Topic getTopicByTitle(String title);

    void updateTopic(Topic topic);

    void removeTopicById(Long id);

    List<Topic> getAllTopicsByUserId(Long userId);


    List<Topic> getTotalListOfTopics();

    List<User> getAllUsersByTopicId(Long topicId);

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
}
