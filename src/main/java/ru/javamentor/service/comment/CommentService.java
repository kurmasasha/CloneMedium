package ru.javamentor.service.comment;

import ru.javamentor.dto.CommentDTO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для работы с комментариями
 *
 * @version 2.0
 * @autor Java Mentor
 */
public interface CommentService {

    /**
     * Метод добавления нового комментария
     *
     * @param commentDTO - DTO комментария
     * @param author     - автор комментария
     * @return - Собранный из DTO комментарий
     */
    Comment addComment(CommentDTO commentDTO, User author);

    /**
     * метод для получения комментария по id
     *
     * @param id - уникальный id комментария
     * @return Comment - объект представляющий комментарий
     */
    Comment getCommentById(Long id);

    /**
     * метод для обновления комментария
     *
     * @param comment - обновленный комментарий
     * @param user    - пользователь обновляющий комментарий
     * @return boolean - удалость обновить комментарий или нет
     */
    boolean updateComment(Comment comment, User user);

    /**
     * метод для удаления комментария
     *
     * @param id - уникальный id комментария
     * @return boolean - удалость удалить комментарий или нет
     */
    boolean removeCommentById(Long id);

    /**
     * Метод получения все дочерних комментариев
     *
     * @param parentId - id родительского комментария
     * @return List<Comment> - список дочерних комментариев
     */
    List<Comment> getAllCommentsByParentId(Long parentId);

    /**
     * метод для получения автора конкретного комментария
     *
     * @param commentId - уникальный id комментария
     * @return User - пользователь написавший данный коментарий
     */
    User getAuthorByCommentId(Long commentId);

    /**
     * метод для получения списка комментариев конкретной статьи
     *
     * @param topicId -  уникальный id статьи
     * @return List - список коммментариев конкретной статьи
     */
    List<Comment> getAllCommentsByTopicId(Long topicId);

    /**
     * метод для добавления или удаления лайка к комменту.
     *
     * @param commentId -  уникальный id комментария
     * @param user      - пользователь, добавляющий или удаляющий лайк
     * @return comment - возвращает изменённый комметарий
     */

    Comment putLikeToComment(Long commentId, User user);

    /**
     * метод для добавления или удаления дизлайка к комменту.
     *
     * @param commentId -  уникальный id комментария
     * @param user      - пользователь, добавляющий или удаляющий лайк
     * @return comment - возвращает изменённый комметарий
     */

    Comment putDislikeToComment(Long commentId, User user);

    /**
     * Метод для првоерки на установление соответствия автора комментарию.
     *
     * @param userId    -  id юзера
     * @param commentId - id комментария
     */
    boolean isAuthorOfComment(Long userId, Long commentId);

    boolean isExist(Long commentId);

}
