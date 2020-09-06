package ru.javamentor.service.comment.best;

import ru.javamentor.model.Comment;

import java.util.List;

public interface BestCommentService {
    List<Comment> bestFiveComment();
}
