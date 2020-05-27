package ru.javamentor.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
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

    public Topic(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToMany(mappedBy = "topicCollection")
    private Set<User> authorsOfTopic;
}
