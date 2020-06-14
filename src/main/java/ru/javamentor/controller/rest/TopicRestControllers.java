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

    @Autowired
    public TopicRestControllers(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/user/totalTopicsList")
    public ResponseEntity<List<Topic>> getTotalTopics( ) {
        return new ResponseEntity<>(topicService.getTotalListOfTopics(), HttpStatus.OK);
    }

    @GetMapping("/admin/TopicsByUser/{id}")
    public ResponseEntity<List<Topic>> getAllTopicsByUserId(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(topicService.getAllTopicsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/user/MyTopics")
    public ResponseEntity<List<Topic>> getAllTopicsOfAuthenticatedUser() {
        return new ResponseEntity<>(topicService.getAllTopicsOfAuthenticatedUser(), HttpStatus.OK);
    }

    @GetMapping("/user/allUsersByTopicId/{id}")
    public ResponseEntity<List<User>> getAllUsersByTopicId(@PathVariable(value = "id") Long topicId) {
        return new ResponseEntity<>(topicService.getAllUsersByTopicId(topicId), HttpStatus.OK);
    }

    @GetMapping("/user/topic/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }

    @PostMapping("/user/topic/add")
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        if (topicService.addTopic(topic.getTitle(), topic.getContent())) {
            return new ResponseEntity<>(topic, HttpStatus.OK);
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

    @DeleteMapping("/user/topic/delete/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        if (topicService.removeTopicById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You can't delete the topic because it doesn't belong to you.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     * @param tag - строковое представление хэштега
     * @param uid - строковое представление id пользователя, связанного с топиками
     * @return список топиков
     */
    @GetMapping("/admin/get-all-topics-by-hashtag/{tag}")
    public ResponseEntity<List<Topic>> getAllTopicsByHashtag(@PathVariable String tag, @RequestHeader String uid) {
        tag = "#" + tag;
        List<Topic> topics = null;
        if (uid.equals("all")) {
            topics = topicService.getAllTopicsByHashtag(tag);
        } else {
            topics = topicService.getAllTopicsOfUserByHashtag(Long.parseLong(uid), tag);
        }
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }
}
