package ru.javamentor.dao.comment;

import ru.javamentor.model.Comment;
import ru.javamentor.model.User;

import java.util.List;

public interface CommentDAO {

    List<Comment> getAllCommentsByTopicId(Long topicId);

    void addComment(Comment comment);

    Comment getCommentById(Long id);

    void updateComment(Comment comment);

    User getAuthorByCommentId(Long commentId);

    void removeCommentById(Long id);

    String getCountCommentsByTopicId(Long topicId);
}
