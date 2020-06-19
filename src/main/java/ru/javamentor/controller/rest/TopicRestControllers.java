package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.TopicService;
import ru.javamentor.service.UserService;

import javax.validation.Valid;
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

    @GetMapping("/free-user/moderatedTopicsList")
    public ResponseEntity<List<Topic>> getTotalTopics( ) {
        return new ResponseEntity<>(topicService.getModeratedTopics(), HttpStatus.OK);
    }

    @GetMapping("/admin/notModeratedTopics")
    public ResponseEntity<List<Topic>> getNotModeratedTopics( ) {
        return new ResponseEntity<>(topicService.getNotModeratedTopics(), HttpStatus.OK);
    }

    //TODO пока жестко задаю количество записей на странице
    @GetMapping("/admin/notModeratedTopicsPage/{page}")
    public ResponseEntity<List<Topic>> getNotModeratedTopicsPage(@PathVariable(value = "page") Integer page) {
        return new ResponseEntity<>(topicService.getNotModeratedTopicsPage(page, 5), HttpStatus.OK);
    }

    @GetMapping("/admin/notModeratedTopicsCount")
    public ResponseEntity<Long> getNotModeratedTopicsCount() {
        return new ResponseEntity<>(topicService.getNotModeratedTopicsCount(), HttpStatus.OK);
    }

    @GetMapping("/user/MyTopics")
    public ResponseEntity<List<Topic>> getAllTopicsOfAuthenticatedUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(topicService.getAllTopicsByUserId(user.getId()), HttpStatus.OK);
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
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topicData, @AuthenticationPrincipal User user) {
        Set<User> users = new HashSet<>();
        users.add(user);
        Topic topic = topicService.addTopic(topicData.getTitle(), topicData.getContent(), users);
        if (topic != null) {
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
     * @param user - данные пользователя, отправившего запрос
     * @return список топиков
     */
    @GetMapping("/free-user/get-topics-of-user-by-hashtag/{tag}")
    public ResponseEntity<List<Topic>> getAllTopicsOfUserByHashtag(@PathVariable String tag, @AuthenticationPrincipal User user) {
        tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsOfUserByHashtag(user.getId(), tag);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    /**
     * Поиск топиков по значению связанного с ними хэштега.
     * @param tag - строковое представление хэштега
     * @return список топиков
     */
    @GetMapping("/free-user/get-all-topics-by-hashtag/{tag}")
    public ResponseEntity<List<Topic>> getAllTopicsByHashtag(@PathVariable String tag) {
        tag = "#" + tag;
        List<Topic> topics = topicService.getAllTopicsByHashtag(tag);
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @DeleteMapping("/admin/topic/delete/{id}")
    public ResponseEntity<String> deleteTopicByAdmin(@PathVariable Long id) {
        if (topicService.removeTopicById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete this topic, try again", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/admin/topic/{id}")
    public ResponseEntity<Topic> getNomoderatedTopicById(@PathVariable Long id) {
        return new ResponseEntity<>(topicService.getTopicById(id), HttpStatus.OK);
    }
}
