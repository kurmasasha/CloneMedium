package ru.javamentor.service;

import ru.javamentor.model.Topic;

import java.util.Set;

public interface TopicService {

    boolean addTopic(Topic topic);

    Topic getTopicById(Long id);

    Topic getTopicByTitle(String title);

    boolean updateTopic(Topic topic);

    void removeTopicById(Long id);

    Set<Topic> getAllTopicsByUserId(Long userId);

}
