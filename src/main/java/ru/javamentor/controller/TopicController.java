package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.javamentor.model.Topic;
import ru.javamentor.service.topic.TopicService;
import ru.javamentor.util.img.LoaderImages;

import java.io.IOException;

/**
 * Контроллер для топика
 *
 * @author Java Mentor
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/topic/*")
public class TopicController {
    final private TopicService topicService;
    private final LoaderImages loaderImages;

    @Autowired
    public TopicController(TopicService topicService, LoaderImages loaderImages) {
        this.topicService = topicService;
        this.loaderImages = loaderImages;
    }

    /**
     * Метод изменения топика
     */
    @PostMapping("/update")
    public String updateTopic(@RequestParam(required = false, value = "id") Long id,
                              @RequestParam(required = false, value = "title") String title,
                              @RequestParam(required = false, value = "content") String content,
                              @RequestParam(required = false, value = "file")MultipartFile file) {
        String imageName = "no-img.png";

        try {
             imageName =  loaderImages.fileToImage(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Topic currentTopic = topicService.getTopicById(id);

        currentTopic.setTitle(title);
        currentTopic.setContent(content);
        currentTopic.setImg(imageName);

        topicService.updateTopic(currentTopic);

        return "redirect:/home";
    }

    /**
     * Метод удаления топика
     */
    @PostMapping("/delete")
    public String deleteTopic(@RequestParam(required = false, value = "id") Long id) {
        topicService.removeTopicById(id);
        return "redirect:/home";
    }
}
