package ru.javamentor.service.topic;

import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dto.TopicDto;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для работы с топиками
 *
 * @version 1.0
 * @author Java Mentor
 */
public interface TopicService {

    /**
     * метод для добавления топика
     *
     * @param title   - название топика
     * @param content - содержимое топика
     * @param completed - статус завершения написния статьи
     * @param img - название картинки
     * @param users   - множество пользователей связанных с добавляемым топиком
     * @return Topic - объект представляющий модель топика
     */
    Topic addTopic(String title, String content, boolean completed, String img, Set<User> users);

    /**
     * метод для получения топика по id
     *
     * @param id - уникальный id топика
     * @return Topic - объект представляющий модель топика
     */
    Topic getTopicById(Long id);

    /**
     * метод для получения топика по названию
     *
     * @param title - название топика
     * @return Topic - объект представляющий модель топика
     */
    Topic getTopicByTitle(String title);

    /**
     * метод для обновления топика
     *
     * @param topic - обновленный топик
     * @return boolean - удалость обновить топик или нет
     */
    boolean updateTopic(Topic topic);

    /**
     * метод для удаления топика
     *
     * @param id - уникальный id топика
     * @return boolean - удалость удалить топик или нет
     */
    boolean removeTopicById(Long id);

    /**
     * метод для получения списка топиков конкретного пользователя
     *
     * @param userId -  уникальный id пользователя топики которого нужно получить
     * @return List топиков этого пользователя
     */
    List<Topic> getAllTopicsByUserId(Long userId);

    /**
     * метод для получения всего списка топиков
     *
     * @return List топиков
     */
    List<Topic> getTotalListOfTopics();

    /**
     * метод для получения списка пользователей связанных с данным топиком
     *
     * @param topicId -  уникальный id топика
     * @return List пользователей связанных с этим топиком
     */
    List<User> getAllUsersByTopicId(Long topicId);

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     *
     * @param value - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsByHashtag(String value);

    /**
     * Поиск топиков пользователя по значению связанного с ними хэштега.
     *
     * @param userId - id пользователя
     * @param value  - строковое представление хэштега
     * @return список топиков
     */
    List<Topic> getAllTopicsOfUserByHashtag(Long userId, String value);

    /**
     * Поиск модерированных топиков
     *
     * @return список топиков
     */
    List<Topic> getModeratedTopics();

    /**
     * Поиск не модерированных топиков
     *
     * @return список топиков
     */
    List<Topic> getNotModeratedTopics();

    /**
     * Поиск не модерированных топиков.
     * Добавлена пагинация.
     *
     * @param page     - номер страницы
     * @param pageSize - размер страницы
     * @return список топиков
     */
    List<Topic> getNotModeratedTopicsPage(int page, int pageSize);

    /**
     * Определение числа  не модерированных топиков
     *
     * @return - число не модерированных топиков
     */
    Long getNotModeratedTopicsCount();

    /**
     * Поиск топиков по теме.
     *
     * @param themesIds - id тем, по которым будем происходить поиск
     * @return список топиков
     */
    List<Topic> getModeratedTopicsByThemes(Set<Long> themesIds);

    /**
     * Метод для добавления лайка
     *
     * @param topicId - id топика
     * @param user - пользователь добавляющий лайк
     * @return Topic - объект представляющий модель топика
     */
    Topic addLikeToTopic(Long topicId, User user);

    /**
     * Метод для добавления дизлайка
     *
     * @param topicId - id топика
     * @param user - пользователь добавляющий дизлайк
     * @return Topic - объект представляющий модель топика
     */
    Topic addDislikeToTopic(Long topicId, User user);

    /**
     * Метод получения списка TopicDto из списка Topic
     * @param topicList - лист топиков
     * @return - список TopicDto
     */
    List<TopicDto> getTopicDtoListByTopicList(List<Topic> topicList);

    @Transactional
    boolean isExist(Long commentId);
}
