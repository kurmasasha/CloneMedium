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
    List<Topic> bestFive(); //todo change doc to this method
}
