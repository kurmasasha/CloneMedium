package ru.javamentor.service.comment.best;

import ru.javamentor.model.Comment;

import java.util.List;

/**
 * Интерфейс для работы с комментариями набравшими
 * наибольшее число лайков
 *
 * @author Java Mentor
 * @version 1.0
 */
public interface BestCommentService {

    /**
     * Метод для получения 5 комментариев
     * набравших наибольшие количество лайков
     *
     * @return List<Comment> - возвращает список из 5 комментариев
     */
    List<Comment> bestFiveComment();
}
