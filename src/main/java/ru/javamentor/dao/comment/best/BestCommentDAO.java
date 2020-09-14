package ru.javamentor.dao.comment.best;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javamentor.model.Comment;

/**
 * Repository для получения лучших комментариев
 *
 * @author Java Mentor
 * @version 1.0
 */
@Repository
public interface BestCommentDAO extends JpaRepository<Comment, Long> {
}