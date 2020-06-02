package ru.javamentor.model;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    final LocalDateTime dateCreated;

    @ManyToMany
    @JoinTable(name = "users_topics", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> authors;

    public Topic(String title, String content, Set<User> authors, LocalDateTime time) {
        this.title = title;
        this.content = content;
        this.authors = authors;
        this.dateCreated = LocalDateTime.now(ZoneId.of("GMT"));
    }
}
