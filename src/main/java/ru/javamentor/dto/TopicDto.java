package ru.javamentor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javamentor.model.Hashtag;
import ru.javamentor.model.Theme;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.LocalDateTime;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy в HH:mm")
    private LocalDateTime dateCreated;

    private boolean isModerate;
    private boolean completed;
    private Integer likes;
    private Integer dislikes;
    private String img;
    private Set<User> authors;
    private Set<Hashtag> hashtags;
    private Set<Theme> themes;
    private String commentsCount = "0";

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
        this.dislikes = topic.getDislikes();
        this.img = topic.getImg();
        this.authors = topic.getAuthors();
        this.hashtags = topic.getHashtags();
        this.themes = topic.getThemes();
    }
}
