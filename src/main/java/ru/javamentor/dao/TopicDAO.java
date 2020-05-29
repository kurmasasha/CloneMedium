package ru.javamentor.dao;

import ru.javamentor.model.Topic;

import java.util.Set;

public interface TopicDAO {

    void addTopic(Topic topic);

    Topic getTopicById(Long id);

    Topic getTopicByTitle(String title);

    void updateTopic(Topic topic);

    void removeTopicById(Long id);

    Set<Topic> getAllTopicsByUserId(Long userId);

    Set<Topic> getAllTopicsOfAllUsers();
}
