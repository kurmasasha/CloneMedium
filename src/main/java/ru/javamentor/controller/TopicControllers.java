package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
=======
import java.util.HashSet;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class TopicControllers {

    private TopicService topicService;

    private UserService userService;

    @Autowired
    public TopicControllers(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }


    @GetMapping("/user/allTopics/{id}")
    public ResponseEntity<Set<Topic>> getAllTopicsByUserId(@PathVariable(value = "id") Long userId) {
=======
    @GetMapping("/user/allTopicsOfUser")
    public ResponseEntity<Set<Topic>> getAllTopicsByUserId(Long userId) {

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

    @PostMapping("/user/topic/update")
    public ResponseEntity<String> updateTopic(Long id, String title, String content, Set<Long> authorsId, Principal principal) {
        if (topicOfUser(id, principal)) {
            Set<User> authorsOfTopic = new HashSet<>();
            for(Long authorId: authorsId){
                User user = userService.getUserById(authorId);
                authorsOfTopic.add(user);
            }
            topicService.updateTopic(new Topic(id, title, content, authorsOfTopic));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean topicOfUser(Long idOfTopic, Principal principal) {
        String username = principal.getName();
        Topic topic = topicService.getTopicById(idOfTopic);
        Set<User> authors = topic.getAuthorsOfTopic();
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
