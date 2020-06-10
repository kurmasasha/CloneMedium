package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicDAO topicDAO;

    @Autowired
    public TopicServiceImpl(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    @Transactional
    @Override
    public boolean addTopic(String title, String content) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<User> users = new HashSet<>();
        users.add(currentUser);
//        Topic topic = new Topic(title, content, users, LocalDateTime.now(ZoneId.of("UTC")));
        Topic topic = new Topic(title, content, users, LocalDateTime.now(), false);
        topicDAO.addTopic(topic);
        log.info("IN addTopic - topic: {} successfully added", topic);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Topic getTopicById(Long id) {
        Topic result = topicDAO.getTopicById(id);
        log.info("IN getTopicById - topic: {} found by id: {}", result, id);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Topic> getTotalListOfTopics() {

        return topicDAO.getTotalListOfTopics().stream().sorted(Comparator.comparing(Topic::getDateCreated).reversed()).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    @Override
    public Topic getTopicByTitle(String title) {
        Topic result = topicDAO.getTopicByTitle(title);
        log.info("IN getTopicByTitle - topic: {} found by title: {}", result, title);
        return result;
    }

    @Transactional
    @Override
    public boolean updateTopic(Topic topic) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> userList = getAllUsersByTopicId(topic.getId());
        if (userList.contains(currentUser)) {
            topicDAO.updateTopic(topic);
            log.info("IN updateTopic - topic with Id: {} successfully updated", topic.getId());
            return true;
        }
        log.warn("IN updateTopic - topic with Id: {} not updated", topic.getId());
        return false;
    }

    @Transactional
    @Override
    public boolean removeTopicById(Long id) {
        /*User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> userList = getAllUsersByTopicId(id);
        if (userList.contains(currentUser)) {*/
            topicDAO.removeTopicById(id);
            log.info("IN removeTopicById - topic with Id: {} successfully deleted", id);
            return true;
        /*}
        log.warn("IN removeTopicById - topic with Id: {} not deleted", id);
        return false;*/
    }

    @Transactional
    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        List<Topic> result = topicDAO.getAllTopicsByUserId(userId);
        log.info("IN getAllTopicsByUserId - {} topics found", result.size());
        return result;
    }

    @Transactional
    @Override
    public List<User> getAllUsersByTopicId(Long topicId) {
        List<User> result = topicDAO.getAllUsersByTopicId(topicId);
        log.info("IN getAllUsersByTopicId - {} authors found", result.size());
        return result;
    }

    @Transactional
    @Override
    public List<Topic> getAllTopicsOfAuthenticatedUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Topic> result = topicDAO.getAllTopicsByUserId(currentUser.getId());
        log.info("IN getAllTopicsOFAuthenticatedUser - {} topics found", result.size());
        return result;
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsByHashtag(String value) {
        List<Topic> result = topicDAO.getAllTopicsByHashtag(value);
        log.info("IN getAllTopicsByHashtag - {} topics found", result.size());
        return result;
    }

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     * @param userId - id пользователя
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value) {
        List<Topic> result = topicDAO.getAllTopicsOfUserByHashtag(userId, value);
        log.info("IN getAllTopicsOfUserByHashtag - {} topics found", result.size());
        return result;
    }
}
