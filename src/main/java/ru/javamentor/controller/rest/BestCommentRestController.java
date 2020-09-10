package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.model.Comment;
import ru.javamentor.service.comment.best.BestCommentService;

import java.util.List;

/**
 * Rest контроллер для получения 5 лучших комментариев
 * по количеству лайков
 *
 * @author Java Mentor
 * @version 1.0
 */
@RestController
@RequestMapping("best/comment")
public class BestCommentRestController {
    private final BestCommentService bestCommentService;

    @Autowired
    public BestCommentRestController(BestCommentService bestCommentService) {
        this.bestCommentService = bestCommentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getBestFiveComment() {
        return new ResponseEntity<>(bestCommentService.bestFiveComment(), HttpStatus.OK);
    }
}
