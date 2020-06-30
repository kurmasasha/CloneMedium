package ru.javamentor.service;

import ru.javamentor.model.Comment;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;

public interface CommentService {

    Comment addComment(String text, User author, Topic topic);

    Comment getCommentById(Long id);

    boolean updateComment(Comment comment, User user);

    boolean removeCommentById(Long id);

    User getAuthorByCommentId(Long commentId);

    List<Comment> getAllCommentsByTopicId(Long topicId);
}
