package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.model.Topic;
import ru.javamentor.service.topic.best.BestTopicsService;

import java.util.List;

/**
 * REST контроллер для получения
 * 5-ти лучших статей по лайкам
 *
 * @version 1.0
 * @author Java Mentor
 */
@RestController
@RequestMapping("/best/topics")
public class BestTopicsRestController {
    private final BestTopicsService bestTopicsService;

    @Autowired
    public BestTopicsRestController(BestTopicsService bestTopicsService) {
        this.bestTopicsService = bestTopicsService;
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getFiveTopicsByLikes(){
        return new ResponseEntity<>(bestTopicsService.bestFive(), HttpStatus.OK);
    }
}
