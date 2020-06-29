package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.CommentDAO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Transactional
    @Override
    public Comment addComment(String text, User author, Topic topic) {
        try {
            Comment comment = new Comment(text, author, topic, LocalDateTime.now());
            commentDAO.addComment(comment);
            log.info("IN addComment - comment: {} successfully added", comment);
            return comment;
        } catch (Exception e) {
            log.warn("IN addComment - comment not added");
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentDAO.getCommentById(id);
        log.info("IN getCommentById - comment: {} found by id: {}", comment, id);
        return comment;
    }

    @Transactional
    @Override
    public boolean updateComment(Comment comment) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = getAuthorByCommentId(comment.getId());
        if (author.equals(currentUser)) {
            commentDAO.updateComment(comment);
            log.info("IN updateComment - comment with Id: {} successfully updated", comment.getId());
            return true;
        }
        log.warn("IN updateComment - comment with Id: {} not updated", comment.getId());
        return false;
    }

    @Transactional
    @Override
    public User getAuthorByCommentId(Long commentId) {
        User author = commentDAO.getAuthorByCommentId(commentId);
        log.info("IN getAuthorByCommentId - {} author found", commentId);
        return author;
    }

    @Transactional
    @Override
    public boolean removeCommentById(Long id) {
        commentDAO.removeCommentById(id);
        log.info("IN removeCommentById - comment with Id: {} successfully deleted", id);
        return true;
    }

    @Transactional
    @Override
    public List<Comment> getAllCommentsByTopicId(Long topicId) {
        List<Comment> comments = commentDAO.getAllCommentsByTopicId(topicId);
        log.info("IN getAllCommentsByTopicId - {} comments found", comments.size());
        return comments;
    }

}
