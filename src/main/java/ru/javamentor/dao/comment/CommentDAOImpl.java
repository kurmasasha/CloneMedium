package ru.javamentor.dao.comment;

import org.springframework.stereotype.Repository;
import ru.javamentor.dao.comment.CommentDAO;
import ru.javamentor.model.Comment;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return entityManager.createQuery("SELECT c.author FROM Comment c WHERE c.id = :commentId", User.class)
                .setParameter("commentId", commentId).getSingleResult();
    }

    @Override
    public void removeCommentById(Long id) {
        entityManager.createQuery("delete from Comment where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * метод для определения количества комментариев у топика
     * @param topicId - id нужного топика
     * @return количесвто комментариев у топика
     */
    @Override
    public String getTopicCommentsCount(Long topicId) {
        return entityManager.createNativeQuery("SELECT COUNT(c.id) FROM comments c WHERE c.topic_id = :topicId")
                .setParameter("topicId", topicId)
                .getSingleResult()
                .toString();
    }

    @Override
    public void removeCommentsByTopicId(long topicId) {
        entityManager.createQuery("DELETE FROM Comment c WHERE c.topic.id = :topicId")
                .setParameter("topicId", topicId).executeUpdate();
    }

    public boolean isExist(Long commentId){
        Long count = entityManager.createQuery("SELECT COUNT(c.id) FROM Comment c WHERE c.id = :commentId", Long.class)
                .setParameter("commentId", commentId)
                .getSingleResult();
        return count>0;
    }

}

