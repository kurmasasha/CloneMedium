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

    List<User> getAllUsersByTopicId(Long topicId);

}
