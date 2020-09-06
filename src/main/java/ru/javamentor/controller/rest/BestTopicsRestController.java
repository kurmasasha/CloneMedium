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
 * @author Java Mentor
 * @version 1.0
 */
@RestController
@RequestMapping("/best/topics")
public class BestTopicsRestController {
    private final BestTopicsService bestTopicsService;

    @Autowired
    public BestTopicsRestController(BestTopicsService bestTopicsService) {
        this.bestTopicsService = bestTopicsService;
    }

    /**
     * Метод для получения списка лучших статей
     * (до 5 штук)
     */
    @GetMapping
    public ResponseEntity<List<Topic>> getFiveTopicsByLikes() {
        return new ResponseEntity<>(bestTopicsService.bestFive(), HttpStatus.OK);
    }
}
