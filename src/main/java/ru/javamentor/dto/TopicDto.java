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
    private Integer likes = 0;
    private String img;
    private Set<UserDto> authors;
    private Set<Hashtag> hashtags;
    private Set<Theme> themes;

    public List<TopicDto> getTopicDtoList(List<Topic> topicList) {
        List<TopicDto> topicDtoList = new ArrayList<>();
        for (Topic topic :
                topicList) {
            authors = new HashSet<>();
            TopicDto topicDto = new TopicDto();
            topicDto.setId(topic.getId());
            topicDto.setTitle(topic.getTitle());
            if (topic.getContent().length() > 800) {
                topicDto.setContent(topic.getContent().substring(0, 800) + "...");
            } else {
                topicDto.setContent(topic.getContent());
            }
            topicDto.setDateCreated(topic.getDateCreated());
            topicDto.setLikes(topic.getLikes());
            topicDto.setImg(topic.getImg());
            for (User user :
                    topic.getAuthors()) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setUsername(user.getUsername());
                userDto.setRole(user.getRole());
                authors.add(userDto);
            }
            topicDto.setAuthors(authors);
            topicDto.setHashtags(topic.getHashtags());
            topicDto.setThemes(topic.getThemes());
            topicDtoList.add(topicDto);
        }
        return topicDtoList;
    }
}
