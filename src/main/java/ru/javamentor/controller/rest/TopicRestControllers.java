package ru.javamentor.controller.rest;

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
public class TopicRestControllers {

    private final TopicService topicService;

    private final UserService userService;

    @Autowired
    public TopicRestControllers(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping("/user/totalTopicsList")
    public ResponseEntity<List<Topic>> getTotalTopics( ) {
        return new ResponseEntity<>(topicService.getTotalListOfTopics(), HttpStatus.OK);
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
    public ResponseEntity<Topic> addTopic(String title, String content) {
        if (topicService.addTopic(title, content)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/topic/update")
    public ResponseEntity<String> updateTopic(@RequestBody Topic topic) {
        if (topicService.updateTopic(topic)) {
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
