package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javamentor.model.Topic;
import ru.javamentor.service.topic.TopicService;

@Controller
@RequestMapping(value = "/temp/topic/*")
public class TempController {
    final private TopicService topicService;

    @Autowired
    public TempController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam(required = false, value = "id") Long id,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "content") String content) {

        Topic currentTopic = topicService.getTopicById(id);
        currentTopic.setTitle(title);
        currentTopic.setContent(content);
        topicService.updateTopic(currentTopic);
        return "redirect:/home";
    }
}
