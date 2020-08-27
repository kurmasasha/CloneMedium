package ru.javamentor.dao.comment;

import ru.javamentor.model.Comment;
import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для доступа к комментариям из базы
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface CommentDAO {

    /**
     * Метод для получения списка комментариев по id топика.
     */
    List<Comment> getAllCommentsByTopicId(Long topicId);

    /**
     * Метод для добавления комментария.
     */
    void addComment(Comment comment);

    /**
     * Метод для получения комментария по id.
     */
    Comment getCommentById(Long id);

    /**
     * Метод для обновления комментария.
     */
    void updateComment(Comment comment);

    /**
     * Метод для получения юзера по id комментария.
     */
    User getAuthorByCommentId(Long commentId);

    /**
     * Метод для удаления комментария по id.
     */
    void removeCommentById(Long id);

    /**
     * Метод для определения количества комментариев у топика.
     */
    String getTopicCommentsCount(Long topicId);

    /**
     * Метод для удаления комментария по id топика.
     */
    void removeCommentsByTopicId(long topicId);

    boolean isExist(Long commentId);
}
