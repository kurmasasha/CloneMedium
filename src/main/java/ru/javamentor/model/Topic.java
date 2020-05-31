package ru.javamentor.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Set<User> authors;

    public Topic(String title, String content, Set<User> authors) {
        this.title = title;
        this.content = content;
        this.authors = authors;
    }


}
