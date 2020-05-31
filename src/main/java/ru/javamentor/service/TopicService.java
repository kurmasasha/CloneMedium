package ru.javamentor.service;

import ru.javamentor.model.Topic;

import java.util.List;
import java.util.Set;

public interface TopicService {

    boolean addTopic(Topic topic);

    Topic getTopicById(Long id);

    Topic getTopicByTitle(String title);

    boolean updateTopic(Long topicId, String TopicTitle, String topicContent);

    void removeTopicById(Long id);

    List<Topic> getAllTopicsByUserId(Long userId);

}
