package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс представляющий модель комментария
 *
 * @author Java Mentor
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String text;

    @Column
    @NotNull
    private Integer likes = 0;

    @Column
    @NotNull
    private Integer dislikes = 0;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "users_comments_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likedUsers;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "users_comments_dislikes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> dislikedUsers;


    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy в HH:mm")
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "main_comment_id")
    private Long mainCommentId;

    @Column(name = "is_main_comment")
    private Boolean isMainComment;

    public Comment(String text, User author, Topic topic, LocalDateTime dateCreated, Boolean isMainComment, Long mainCommentId) {
        this.text = text;
        this.author = author;
        this.topic = topic;
        this.dateCreated = dateCreated;
        this.isMainComment = isMainComment;
        this.mainCommentId = mainCommentId;

    }

    public Comment(String text, User author, Topic topic, LocalDateTime dateCreated) {
        this.text = text;
        this.author = author;
        this.topic = topic;
        this.dateCreated = dateCreated;
    }

}
