package ru.javamentor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javamentor.model.Hashtag;
import ru.javamentor.model.Theme;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс представляющий объект DTO топика
 *
 * @version 1.0
 * @author Java Mentor
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private boolean isModerate;
    private boolean completed;
    private Integer likes;
    private String img;
    private Set<User> authors;
    private Set<Hashtag> hashtags;
    private Set<Theme> themes;

    public TopicDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        if (topic.getContent().length() > 800) {
            this.setContent(topic.getContent().substring(0, 800) + "...");
        } else {
            this.content = topic.getContent();
        }
        this.dateCreated = topic.getDateCreated();
        this.isModerate = topic.isModerate();
        this.completed = topic.isCompleted();
        this.likes = topic.getLikes();
        this.img = topic.getImg();
        this.authors = topic.getAuthors();
        this.hashtags = topic.getHashtags();
        this.themes = topic.getThemes();
    }
}
