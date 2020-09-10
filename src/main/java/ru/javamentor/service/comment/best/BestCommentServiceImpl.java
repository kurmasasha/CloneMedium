package ru.javamentor.service.comment.best;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.comment.best.BestCommentDAO;
import ru.javamentor.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса BestCommentService
 *
 * @author Java Mentor
 * @version 1.0
 */
@Service
public class BestCommentServiceImpl implements BestCommentService {
    private final BestCommentDAO bestCommentDAO;

    @Autowired
    public BestCommentServiceImpl(BestCommentDAO bestCommentDAO) {
        this.bestCommentDAO = bestCommentDAO;
    }

    @Transactional
    @Override
    public List<Comment> bestFiveComment() {
        return bestCommentDAO.findAll(Sort.by(Sort.Direction.DESC, "likes"))
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}
