package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.TopicDAO;
import ru.javamentor.model.Topic;

import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    TopicDAO topicDAO;

    @Autowired
    public TopicServiceImpl(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    @Transactional
    @Override
    public boolean addTopic(Topic topic) {
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
        topicDAO.updateTopic(topic);
        return true;
    }

    @Transactional
    @Override
    public void removeTopicById(Long id) {
        topicDAO.removeTopicById(id);
    }

    @Override
    public Set<Topic> getAllTopicsByUserId(Long userId) {
        return topicDAO.getAllTopicsByUserId(userId);
    }
}
