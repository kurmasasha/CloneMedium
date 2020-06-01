package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.TopicDAO;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.*;
import java.util.*;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicDAO topicDAO;
    private final UserDAO userDAO;


    @Autowired
    public TopicServiceImpl(TopicDAO topicDAO, UserDAO userDAO) {
        this.topicDAO = topicDAO;
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public boolean addTopic(String title, String content) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<User> users = new HashSet<>();
        users.add(currentUser);
        Topic topic = new Topic(title, content, users, LocalDateTime.now());
        topicDAO.addTopic(topic);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Topic getTopicById(Long id) {
        return topicDAO.getTopicById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Topic getTopicByTitle(String title) {
        return topicDAO.getTopicByTitle(title);
    }

    @Transactional
    @Override
    public boolean updateTopic(Topic topic) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> userList = userDAO.getAllUsersByTopicId();
        if (userList.contains(currentUser)) {
            topicDAO.updateTopic(topic);
            return true;
        }
        return true;
    }

    @Transactional
    @Override
    public void removeTopicById(Long id) {
        topicDAO.removeTopicById(id);
    }

    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        return topicDAO.getAllTopicsByUserId(userId);
    }
}
