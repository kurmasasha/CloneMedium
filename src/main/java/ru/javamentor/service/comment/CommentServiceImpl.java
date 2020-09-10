package ru.javamentor.service.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.comment.CommentDAO;
import ru.javamentor.dao.user.UserDAO;
import ru.javamentor.dto.CommentDTO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Notification;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.notification.NotificationService;
import ru.javamentor.service.notification.WsNotificationService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация интерфейса для работы с комментариями
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private final TopicService topicService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final WsNotificationService wsNotificationService;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO,
                              UserDAO userDAO,
                              TopicService topicService,
                              UserService userService,
                              NotificationService notificationService, WsNotificationService wsNotificationService) {
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
        this.topicService = topicService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.wsNotificationService = wsNotificationService;
    }

    /**
     * метод для добавления комментария
     *
     * @param author - добавляемый автор комментария
     * @return Comment - возвращает добавленный комментарий
     */
    @Transactional
    @Override
    public Comment addComment(CommentDTO commentDTO, User author) {
        try {
//            checkEmptyComment(commentDTO);

            Comment comment = commentDTOToComment(commentDTO, author);
            commentDAO.addComment(comment);

            sendNotification(topicService.getTopicById(commentDTO.getTopicId()), author);

            log.debug("IN addComment - comment: {} with author.id: {} and author.userName: {} successfully added",
                    commentDTO.getText(), author.getId(), author.getUsername());

            return comment;
        } catch (Exception e) {
            log.error("IN addComment - comment not added with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    private void checkEmptyComment(CommentDTO commentDTO) throws Exception {
        if (commentDTO.getText().isEmpty()) {
            throw new Exception();
        }
    }

    private Comment commentDTOToComment(CommentDTO commentDTO, User author) {
        return new Comment(commentDTO.getText(),
                author,
                topicService.getTopicById(commentDTO.getTopicId()),
                LocalDateTime.now(), commentDTO.getIsMainComment(),
                commentDTO.getMainCommentId());
    }

    private void sendNotification(Topic topic, User author) {
        for (User u : topic.getAuthors()) {
            Notification notification = new Notification();

            notification.setTitle("Новый комментарий");
            notification.setText("Пользователь " + userService.getUserByEmail(author.getUsername()) +
                    " оставил новый комментарий в статье " + topic.getTitle() + " ");
            notification.setUser(u);

            notificationService.addNotification(notification);

            wsNotificationService.sendNotification(u, notificationService.getNotificationDto
                    (notificationService.getById(notification.getId())));
        }
    }

    /**
     * метод для получения комментария по id
     *
     * @param id - уникальный id комментария
     * @return Comment - объект представляющий комментарий
     */
    @Transactional(readOnly = true)
    @Override
    public Comment getCommentById(Long id) {
        try {
            Comment comment = commentDAO.getCommentById(id);
            log.debug("IN getCommentById - comment text: {} found by id: {} with author.id {} and author.name {}",
                    comment.getText(), id, comment.getAuthor().getId(), comment.getAuthor().getUsername());
            return comment;
        } catch (Exception e) {
            log.error("Exception while getCommentById in service with comment.id is {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для обновления комментария
     *
     * @param comment - обновленный комментарий
     * @param user    - пользователь обновляющий комментарий
     * @return boolean - удалость обновить комментарий или нет
     */
    @Transactional
    @Override
    public boolean updateComment(Comment comment, User user) {
        User author = getAuthorByCommentId(comment.getId());
        if (author.equals(user)) {
            commentDAO.updateComment(comment);
            log.debug("IN updateComment - comment.id: {} with user.id: {} and user.userName: {} successfully updated",
                    comment.getId(), user.getId(), user.getUsername());
            return true;
        }
        log.debug("IN updateComment - comment with Id: {} not updated", comment.getId());
        return false;
    }

    /**
     * метод для получения автора конкретного комментария
     *
     * @param commentId - уникальный id комментария
     * @return User - пользователь написавший данный коментарий
     */
    @Transactional
    @Override
    public User getAuthorByCommentId(Long commentId) {
        try {
            User author = commentDAO.getAuthorByCommentId(commentId);
            log.debug("IN getAuthorByCommentId - comment.id: {} found author.id: {} and author.name: {}",
                    commentId, author.getId(), author.getUsername());
            return author;
        } catch (Exception e) {
            log.error("Exception while getAuthorByCommentId in service with comment.id {}", commentId);
            throw new RuntimeException();
        }
    }

    /**
     * метод для удаления комментария
     *
     * @param id - уникальный id комментария
     * @return boolean - удалость удалить комментарий или нет
     */
    @Transactional
    @Override
    public boolean removeCommentById(Long id) {
        try {
            commentDAO.removeCommentById(id);
            log.debug("IN removeCommentById - comment with Id: {} successfully deleted", id);
            return true;
        } catch (Exception e) {
            log.error("Exception while removeCommentById in service with comment.id {}", id);
            return false;
        }
    }

    /**
     * метод для получения списка комментариев конкретной статьи
     *
     * @param topicId -  уникальный id статьи
     * @return List - список коммментариев конкретной статьи
     */
    @Transactional
    @Override
    public List<Comment> getAllCommentsByTopicId(Long topicId) {
        try {
            List<Comment> comments = commentDAO.getAllCommentsByTopicId(topicId);
            log.debug("IN getAllCommentsByTopicId - {} comments found with topic.id: {}", comments.size(), topicId);
            return comments;
        } catch (Exception e) {
            log.error("Exception while getAllCommentsByTopicId in service with topic.id {}", topicId);
            throw new RuntimeException();
        }
    }

    /**
     * метод для добавления или удаления лайка к комменту.
     *
     * @param commentId -  уникальный id комментария
     * @param user      - пользователь, добавляющий или удаляющий лайк
     * @return comment - возвращает изменённый комметарий
     */

    @Transactional
    @Override
    public Comment putLikeToComment(Long commentId, User user) {
        try {
            Comment comment = commentDAO.getCommentById(commentId);
            boolean isLiked = comment.getLikedUsers()
                    .stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
            boolean isDisliked = comment.getDislikedUsers()
                    .stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));

            if (isLiked) {
                comment.getLikedUsers().remove(userDAO.getUserById(user.getId()));
                comment.setLikes(comment.getLikes() - 1);
                commentDAO.updateComment(comment);
                log.debug("IN putLikeToComment - likes in comment.id: {} was decreased by user.id: {}", commentId, user.getId());
            } else if (isDisliked) {
                comment.getDislikedUsers().remove(userDAO.getUserById(user.getId()));
                comment.setDislikes(comment.getDislikes() - 1);
                comment.getLikedUsers().add(user);
                comment.setLikes(comment.getLikes() + 1);
                commentDAO.updateComment(comment);
                log.debug("IN putLikeToComment - likes in comment.id: {} was increased by user.id: {}", commentId, user.getId());
            } else {
                comment.getLikedUsers().add(user);
                comment.setLikes(comment.getLikes() + 1);
                commentDAO.updateComment(comment);
                log.debug("IN putLikeToComment - likes in comment.id: {} was increased by user.id: {}", commentId, user.getId());
            }
            return comment;
        } catch (Exception e) {
            log.error("Exception while addOrDeleteLike in service with comment.id {}", commentId);
            throw new RuntimeException();
        }
    }

    /**
     * метод для добавления или удаления дизлайка к комменту.
     *
     * @param commentId -  уникальный id комментария
     * @param user      - пользователь, добавляющий или удаляющий лайк
     * @return comment - возвращает изменённый комметарий
     */
    @Transactional
    @Override
    public Comment putDislikeToComment(Long commentId, User user) {

        try {
            Comment comment = commentDAO.getCommentById(commentId);
            boolean isLiked = comment.getLikedUsers()
                    .stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
            boolean isDisliked = comment.getDislikedUsers()
                    .stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));

            if (isLiked) {
                comment.getLikedUsers().remove(userDAO.getUserById(user.getId()));
                comment.setLikes(comment.getLikes() - 1);
                comment.getDislikedUsers().add(user);
                comment.setDislikes(comment.getDislikes() + 1);
                commentDAO.updateComment(comment);
                log.debug("IN putDislikeToComment - dislikes in comment.id: {} was increased by user.id: {}", commentId, user.getId());
            } else if (isDisliked) {
                comment.getDislikedUsers().remove(userDAO.getUserById(user.getId()));
                comment.setDislikes(comment.getDislikes() - 1);
                commentDAO.updateComment(comment);
                log.debug("IN putDislikeToComment - dislikes in comment.id: {} was decreased by user.id: {}", commentId, user.getId());
            } else {
                comment.getDislikedUsers().add(user);
                comment.setDislikes(comment.getDislikes() + 1);
                commentDAO.updateComment(comment);
                log.debug("IN putDislikeToComment - dislikes in comment.id: {} was increased by user.id: {}", commentId, user.getId());
            }
            return comment;
        } catch (Exception e) {
            log.error("Exception while addOrDeleteLike in service with comment.id {}", commentId);
            throw new RuntimeException();
        }
    }

    /**
     * Метод для првоерки на установление соответствия автора комментарию.
     *
     * @param userId    -  id юзера
     * @param commentId - id комментария
     */
    @Override
    public boolean isAuthorOfComment(Long userId, Long commentId) {
        try {
            User user = commentDAO.getAuthorByCommentId(commentId);
            return (user.getId().equals(userId));
        } catch (Exception e) {
            log.error("Exception while isAuthorOfComment in service with comment.id is {}", commentId);
            throw new RuntimeException();
        }

    }

    public boolean isExist(Long commentId) {
        return commentDAO.isExist(commentId);
    }

}
