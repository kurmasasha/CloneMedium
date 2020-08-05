package ru.javamentor.dao.comment;

import org.springframework.stereotype.Repository;
import ru.javamentor.dao.comment.CommentDAO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> getAllCommentsByTopicId(Long topicId) {
        return entityManager.createQuery("SELECT c FROM Comment c LEFT JOIN FETCH c.topic t WHERE t.id = :topicId GROUP BY c.id ORDER BY c.dateCreated  DESC", Comment.class)
                .setParameter("topicId", topicId).getResultList();
    }

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public void updateComment(Comment comment) {
        entityManager.merge(comment);
    }

    @Override
    public User getAuthorByCommentId(Long commentId) {
        return (User) entityManager.createQuery("SELECT u FROM Comment c LEFT JOIN FETCH c.author u WHERE c.id = :commentId", User.class)
                .setParameter("commentId", commentId);
    }

    @Override
    public void removeCommentById(Long id) {
        Comment comment = getCommentById(id);
        entityManager.remove(comment);
    }

    @Override
    public boolean isExist(Long commentId) {
        Query count = entityManager.createQuery("SELECT COUNT(c.id) FROM Comment c WHERE c.id = :commentId")
                .setParameter("commentId", commentId);
        long res = Long.parseLong(String.valueOf(count));
        return res > 0;
    }

}

