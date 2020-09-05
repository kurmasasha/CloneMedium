package ru.javamentor.service.topic.best;

import ru.javamentor.model.Topic;

import java.util.List;

/**
 * Интерфейс для получения лучших статей
 *
 * @author Java Mentor
 * @version 1.0
 */
public interface BestTopicsService {

    /**
     * Метод для получения 5-ти статей,
     * набравших наибольшее количество лайков
     *
     * @return List<Topic> - коллекция содержащая до 5-ти лучший статей
     */
    List<Topic> bestFive();

    /**
     * Метод сортировки статей по количеству лайков
     *
     * @param bestTopics - неотсортированный список статей
     * @return List<Topic> - коллекция сортированных по количеству
     * лайков статей
     */
    List<Topic> sortByLikes(List<Topic> bestTopics);
}
