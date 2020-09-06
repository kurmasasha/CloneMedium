package ru.javamentor.service.comment.best;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.javamentor.dao.comment.best.BestCommentDAO;
import ru.javamentor.model.Comment;

import java.util.List;

@Service
public class BestCommentServiceImpl implements BestCommentService{
    private final BestCommentDAO bestCommentDAO;

    @Autowired
    public BestCommentServiceImpl(BestCommentDAO bestCommentDAO) {
        this.bestCommentDAO = bestCommentDAO;
    }

    @Override
    public List<Comment> bestFiveComment() {
        return bestCommentDAO.findAll(Sort.by(Sort.Direction.DESC, "likes"));
    }
}
