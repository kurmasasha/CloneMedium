package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String title;

    @Column
    String content;

    @ManyToMany
    @JoinTable(name = "users_topics", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> authorsOfTopic;

    public Topic(String title, String content) {
        this.title = title;
        this.content = content;
    }


    @ManyToMany(mappedBy = "topicCollection")
    @JsonIgnore
    private Set<User> authorsOfTopic;
    public Topic(Long id, String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Topic(String title, String content, Set<User> authorsOfTopic) {
        this.title = title;
        this.content = content;
        this.authorsOfTopic = authorsOfTopic;
    }

}
