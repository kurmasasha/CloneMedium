package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TopicControllers {

    private TopicService topicService;

    @Autowired
    public TopicControllers(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/user/allTopics/{id}")
    public ResponseEntity<Set<Topic>> getAllTopicsByUserId(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(topicService.getAllTopicsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/user/topic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }

    @PostMapping("/user/topic/add")
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/topic/update")
    public ResponseEntity<String> updateTopic(@RequestBody Topic topic, Principal principal) {
        if (topicOfUser(topic, principal)) {
            topicService.updateTopic(topic);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't update the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean topicOfUser(Topic topic, Principal principal) {
        String username = principal.getName();
        Set<User> authors = topic.getAuthorsOfTopic();
        return authors.contains(username);
    }

    @PostMapping("/user/topic/delete/{id}")
    public ResponseEntity<String> updateTopic(@PathVariable Long id, Principal principal) {
        Topic currentTopic = topicService.getTopicById(id);
        if (topicOfUser(currentTopic, principal)) {
            topicService.removeTopicById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }
}
