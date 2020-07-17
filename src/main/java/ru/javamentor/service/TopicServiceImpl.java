package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.TopicDAO;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса TopicService
 *
 * @version 1.0
 * @author Java Mentor
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
     * @param completed - статус завершения написния статьи
     * @param users   - множество пользователей связанных с добавляемым топиком
     * @param img - название картинки
     * @return Topic - объект представляющий модель топика
     */
    @Transactional
    @Override
    public Topic addTopic(String title, String content, boolean completed, String img, Set<User> users) {
        try {
            Topic topic = new Topic(title, content, completed, img, users, LocalDateTime.now(), false);
            topicDAO.addTopic(topic);
            log.debug("IN addTopic - topic.id: {} and topic.title: {} successfully added", topic.getId(), topic.getTitle());
            return topic;
        } catch (Exception e) {
            log.error("IN addTopic - topic not added with exception {}", e.getMessage());
            throw new RuntimeException();
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
        try {
            var result = topicDAO.getTopicById(id);
            log.debug("IN getTopicById - topic.title: {} found by id: {}", result.getTitle(), id);
            return result;
        } catch (Exception e) {
            log.error("Exception while getTopicById in service with topic.id is {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения всего списка топиков
     *
     * @return List топиков
     */
    @Transactional(readOnly = true)
    @Override
    public List<Topic> getTotalListOfTopics() {
        try {
            List<Topic> result = topicDAO.getTotalListOfTopics();
            log.debug("IN getTotalListOfTopics - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getTotalListOfTopics in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
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
        try {
            var result = topicDAO.getTopicByTitle(title);
            log.debug("IN getTopicByTitle - topic.id: {} found by title: {}", result.getId(), title);
            return result;
        } catch (Exception e) {
            log.error("Exception while getTopicByTitle in service with title {}", title);
            throw new RuntimeException();
        }
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
        try {
            topicDAO.updateTopic(topic);
            log.debug("IN updateTopic - topic with Id: {} successfully updated", topic.getId());
            return true;
        } catch (Exception e) {
            log.error("Exception while updateTopic in service with topic.id is {}", topic.getId());
            throw new RuntimeException();
        }
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
        try {
            topicDAO.removeTopicById(id);
            log.debug("IN removeTopicById - topic with Id: {} successfully deleted", id);
            return true;
        } catch (Exception e) {
            log.error("Exception while removeTopicById in service with topic.id is {}", id);
            throw new RuntimeException();
        }
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
        try {
            List<Topic> result = topicDAO.getAllTopicsByUserId(userId);
            log.debug("IN getAllTopicsByUserId - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllTopicsByUserId in service with user.id is {}", userId);
            throw new RuntimeException();
        }
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
        try {
            List<User> result = topicDAO.getAllUsersByTopicId(topicId);
            log.debug("IN getAllUsersByTopicId - {} authors found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllUsersByTopicId in service with topic.id is {}", topicId);
            throw new RuntimeException();
        }
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    @Override
    public List<Topic> getAllTopicsByHashtag(String value) {
        try {
            List<Topic> result = topicDAO.getAllTopicsByHashtag(value);
            log.debug("IN getAllTopicsByHashtag - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllTopicsByHashtag in service with hashtag is {}", value);
            throw new RuntimeException();
        }
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
        try {
            List<Topic> result = topicDAO.getAllTopicsOfUserByHashtag(userId, value);
            log.debug("IN getAllTopicsOfUserByHashtag - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllTopicsOfUserByHashtag in service with user.id is {}, with hashtag is {}",
                    userId, value);
            throw new RuntimeException();
        }
    }

    /**
     * Поиск модерированных топиков
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getModeratedTopics() {
        try {
            List<Topic> result = topicDAO.getModeratedTopics();
            log.debug("IN getModeratedTopics - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getModeratedTopics in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Поиск не модерированных топиков
     *
     * @return список топиков
     */
    @Override
    public List<Topic> getNotModeratedTopics() {
        try {
            List<Topic> result = topicDAO.getNotModeratedTopics();
            log.debug("IN getNotModeratedTopics - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getNotModeratedTopics in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
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
        try {
            List<Topic> result = topicDAO.getNotModeratedTopicsPage(page, pageSize);
            log.debug("IN getNotModeratedTopicsPage - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getNotModeratedTopicsPage in service with page is {}, with pageSize is {}",
                    page, pageSize);
            throw new RuntimeException();
        }
    }

    /**
     * Определение числа не модерированных топиков
     *
     * @return - число не модерированных топиков
     */
    @Override
    public Long getNotModeratedTopicsCount() {
        try {
            Long result = topicDAO.getNotModeratedTopicsCount();
            log.debug("IN getNotModeratedTopicsCount - {} topics count", result);
            return result;
        } catch (Exception e) {
            log.error("Exception while getNotModeratedTopicsCount in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Поиск топиков по теме.
     * @param themesIds - id тем, по которым будем происходить поиск
     * @return список топиков
     */
    @Transactional
    @Override
    public List<Topic> getModeratedTopicsByThemes(Set<Long> themesIds) {
        try {
            List<Topic> result = topicDAO.getModeratedTopicsByTheme(themesIds);
            log.debug("IN getModeratedTopicsByThemes - {} topics found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getModeratedTopicsByThemes in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Метод для увеличения лайков конкретного топика на 1, в рамках одной сессии
     *
     * @param topicId - уникальный id конкретного топика
     * @return Topic - топик с обновленным количеством лайков
     */
    @Transactional
    @Override
    public Topic increaseTopicLikes(Long topicId) {
        log.debug("IN increaseTopicLikes in service");
        Topic currentTopic = topicDAO.getTopicById(topicId);
        Integer likes = currentTopic.getLikes();
        likes++;
        currentTopic.setLikes(likes);
        topicDAO.updateTopic(currentTopic);
        log.debug("IN increaseTopicLikes - topic.id: {} increase like", topicId);
        return currentTopic;
    }

    /**
     * Метод для уменьшения лайков конкретного топика на 1, в рамках одной сессии
     *
     * @param topicId - уникальный id конкретного топика
     * @return Topic - топик с обновленным количеством лайков
     */
    @Transactional
    @Override
    public Topic decreaseTopicLikes(Long topicId) {
        log.debug("IN decreaseTopicLikes in service");
        Topic currentTopic = topicDAO.getTopicById(topicId);
        Integer likes = currentTopic.getLikes();
        likes--;
        currentTopic.setLikes(likes);
        topicDAO.updateTopic(currentTopic);
        log.debug("IN increaseTopicLikes - topic.id: {} decrease like", topicId);
        return currentTopic;
    }
}
