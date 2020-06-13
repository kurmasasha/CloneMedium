package ru.javamentor.model;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.util.Set;

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

    @ManyToMany
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

}
