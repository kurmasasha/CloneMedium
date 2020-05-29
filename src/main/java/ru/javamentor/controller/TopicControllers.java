package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TopicControllers {

    private final TopicService topicService;

    private final UserService userService;

    @Autowired
    public TopicControllers(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }


    @GetMapping("/user/allTopics/{id}")
    public ResponseEntity<List<Topic>> getAllTopicsByUserId(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(topicService.getAllTopicsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/user/topic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }

    @PostMapping("/user/topic/add")
    public ResponseEntity<Topic> addTopic(String title, String content, @RequestParam(value = "authorsId") Set<Long> authorsId) {
        Set<User> authorsOfTopic = new HashSet<>();
        for (Long id : authorsId) {
            User user = userService.getUserById(id);
            authorsOfTopic.add(user);
        }
        topicService.addTopic(new Topic(title, content, authorsOfTopic));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/topic/update/{topicId}")
    public ResponseEntity<String> updateTopic(@PathVariable Long topicId, String topicTitle, String topicContent) {
        if (topicService.updateTopic(topicId, topicTitle, topicContent)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean topicOfUser(Long idOfTopic, Principal principal) {
        String username = principal.getName();
        Topic topic = topicService.getTopicById(idOfTopic);
        Set<User> authors = topic.getAuthors();
        return authors.contains(username);
    }

    @DeleteMapping("/user/topic/delete/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id, Principal principal) {
        if (topicOfUser(id, principal)) {
            topicService.removeTopicById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }
}
