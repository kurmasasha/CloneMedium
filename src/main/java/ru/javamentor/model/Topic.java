package ru.javamentor.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс представляющий модель топика
 *
 * @version 1.0
 * @autor Java Mentor
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    @NotNull
    private String title;

    @Column
    @NotEmpty
    @NotNull
    private String content;

    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @Column
    private boolean isModerate = false;

    @Column
    @NotNull
    private Integer likes = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_topics", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> authors;

    @ManyToMany
    @JoinTable(name = "hashtags_topics", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<Hashtag> hashtags;

    @PreRemove
    public void preRemove() {
        for (Hashtag tag : hashtags) {
            Set<Topic> topics = tag.getTopics();
            topics.remove(this);
        }
    }

    public Topic(String title, String content, Set<User> authors, LocalDateTime dateCreated, boolean isModerate) {
        this.title = title;
        this.content = content;
        this.authors = authors;
        this.dateCreated = dateCreated;
        this.isModerate = isModerate;
    }

    public Topic(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
