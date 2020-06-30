package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.TopicDAO;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса TopicService
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicDAO topicDAO;

    @Autowired
    public TopicServiceImpl(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    /**
     * метод для добавления топика
     *
     * @param title   - название топика
     * @param content - содержимое топика
     * @param users   - множество пользователей связанных с добавляемым топиком
     * @return Topic - объект представляющий модель топика
     */
    @Transactional
    @Override
    public Topic addTopic(String title, String content, Set<User> users) {
        try {
            Topic topic = new Topic(title, content, users, LocalDateTime.now(), false);
            topicDAO.addTopic(topic);
            log.info("IN addTopic - topic: {} successfully added", topic);
            return topic;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * метод для получения топика по id
     *
     * @param id - уникальный id топика
     * @return Topic - объект представляющий модель топика
     */
    @Transactional(readOnly = true)
    @Override
    public Topic getTopicById(Long id) {
        Topic result = topicDAO.getTopicById(id);
        log.info("IN getTopicById - topic: {} found by id: {}", result, id);
        return result;
    }

    /**
     * метод для получения всего списка топиков
     *
     * @return List топиков
     */
    @Transactional(readOnly = true)
    @Override
    public List<Topic> getTotalListOfTopics() {
        List<Topic> result = topicDAO.getTotalListOfTopics();
        log.info("IN getTotalListOfTopics - {} topics found", result.size());
        return result;
    }

    /**
     * метод для получения топика по названию
     *
     * @param title - название топика
     * @return Topic - объект представляющий модель топика
     */
    @Transactional(readOnly = true)
    @Override
    public Topic getTopicByTitle(String title) {
        Topic result = topicDAO.getTopicByTitle(title);
        log.info("IN getTopicByTitle - topic: {} found by title: {}", result, title);
        return result;
    }

    /**
     * метод для обновления топика
     *
     * @param topic - обновленный топик
     * @return boolean - удалость обновить топик или нет
     */
    @Transactional
    @Override
    public boolean updateTopic(Topic topic) {
        /*User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> userList = getAllUsersByTopicId(topic.getId());
        if (userList.contains(currentUser)) {*/
            topicDAO.updateTopic(topic);
            log.info("IN updateTopic - topic with Id: {} successfully updated", topic.getId());
            return true;
        /*}
        log.warn("IN updateTopic - topic with Id: {} not updated", topic.getId());
        return false;*/
    }

    /**
     * метод для удаления топика
     *
     * @param id - уникальный id топика
     * @return boolean - удалость удалить топик или нет
     */
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

    /**
     * метод для получения списка топиков конкретного пользователя
     *
     * @param userId -  уникальный id пользователя топики которого нужно получить
     * @return List топиков этого пользователя
     */
    @Transactional
    @Override
    public List<Topic> getAllTopicsByUserId(Long userId) {
        List<Topic> result = topicDAO.getAllTopicsByUserId(userId);
        log.info("IN getAllTopicsByUserId - {} topics found", result.size());
        return result;
    }

    /**
     * метод для получения списка пользователей связанных с данным топиком
     *
     * @param topicId -  уникальный id топика
     * @return List пользователей связанных с этим топиком
     */
    @Transactional
    @Override
    public List<User> getAllUsersByTopicId(Long topicId) {
        List<User> result = topicDAO.getAllUsersByTopicId(topicId);
        log.info("IN getAllUsersByTopicId - {} authors found", result.size());
        return result;
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
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
     *
     * @param userId - id пользователя
     * @param value  - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value) {
        List<Topic> result = topicDAO.getAllTopicsOfUserByHashtag(userId, value);
        log.info("IN getAllTopicsOfUserByHashtag - {} topics found", result.size());
        return result;
    }

    /**
     * Поиск модерированных топиков
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getModeratedTopics() {
        List<Topic> result = topicDAO.getModeratedTopics();
        log.info("IN getModeratedTopics - {} topics found", result.size());
        return result;
    }

    /**
     * Поиск не модерированных топиков
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopics() {
        List<Topic> result = topicDAO.getNotModeratedTopics();
        log.info("IN getNotModeratedTopics - {} topics found", result.size());
        return result;
    }

    /**
     * Поиск не модерированных топиков.
     * Добавлена пагинация.
     *
     * @param page     - номер страницы
     * @param pageSize - размер страницы
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopicsPage(int page, int pageSize) {
        List<Topic> result = topicDAO.getNotModeratedTopicsPage(page, pageSize);
        log.info("IN getNotModeratedTopicsPage - {} topics found", result.size());
        return result;
    }

    /**
     * Определение числа не модерированных топиков
     *
     * @return
     */
    @Override
    public Long getNotModeratedTopicsCount() {
        return topicDAO.getNotModeratedTopicsCount();
    }

    @Transactional
    @Override
    public Topic increaseTopicLikes(Long topicId) {
        Topic currentTopic = topicDAO.getTopicById(topicId);
        Integer likes = currentTopic.getLikes();
        likes++;
        currentTopic.setLikes(likes);
        topicDAO.updateTopic(currentTopic);
        return currentTopic;
    }

    @Transactional
    @Override
    public Topic decreaseTopicLikes(Long topicId) {
        Topic currentTopic = topicDAO.getTopicById(topicId);
        Integer likes = currentTopic.getLikes();
        likes--;
        currentTopic.setLikes(likes);
        topicDAO.updateTopic(currentTopic);
        return currentTopic;
    }
}
