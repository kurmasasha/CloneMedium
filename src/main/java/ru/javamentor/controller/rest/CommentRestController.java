package ru.javamentor.controller.rest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Comment;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.comment.CommentService;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private final UserService userService;
    private final CommentService commentService;
    private final TopicService topicService;

    @Autowired
    public CommentRestController(UserService userService, CommentService commentService, TopicService topicService) {
        this.userService = userService;
        this.commentService = commentService;
        this.topicService = topicService;
    }

    @GetMapping("/free-user/allCommentsOfTopic/{id}")
    public ResponseEntity<List<Comment>> getAllUsersByTopicId(@PathVariable(value = "id") Long topicId) {
        return new ResponseEntity<>(commentService.getAllCommentsByTopicId(topicId), HttpStatus.OK);
    }

    @PostMapping("/user/comment/add")
    public ResponseEntity<Comment> addTopic(@RequestBody String data, @AuthenticationPrincipal User user) {
        JSONObject jsonObj = new JSONObject(data);
        Long topicId = Long.parseLong(jsonObj.getString("topicId"));
        String comment = jsonObj.getString("comment");
        Topic topic = topicService.getTopicById(topicId);
        if (user == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Comment newComment = commentService.addComment(comment, userService.getUserByEmail(auth.getName()), topic);
            if (newComment != null) {
                return new ResponseEntity<>(newComment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            Comment newComment = commentService.addComment(comment, user, topic);
            if (newComment != null) {
                return new ResponseEntity<>(newComment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/user/comment/update")
    public ResponseEntity<String> updateComment(@RequestBody Comment comment, @AuthenticationPrincipal User user) {
        if (commentService.updateComment(comment, user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the comment because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/comment/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        if (commentService.removeCommentById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the comment because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/admin/comment/delete/{id}")
    public ResponseEntity<String> deleteCommentByAdmin(@PathVariable Long id) {
        if (commentService.removeCommentById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete this comment, try again", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment/addLike/{commentId}")
    public ResponseEntity<Comment> putLikeToComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        Comment comment = commentService.putLikeToComment(commentId, user);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/comment/addDislike/{commentId}")
    public ResponseEntity<Comment> putDislikeToComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        Comment comment = commentService.putDislikeToComment(commentId, user);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
