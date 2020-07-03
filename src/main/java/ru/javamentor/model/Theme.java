package ru.javamentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.thymeleaf.spring5.expression.Themes;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


/**
 * Класс представляющий модель темы топика
 *
 * @version 1.0
 * @author Java Mentor
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    @NotNull
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "topics_themes", joinColumns = @JoinColumn(name = "theme_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics;

    @PreRemove
    public void preRemove() {
        for (Topic topic : topics) {
            Set<Theme> themes = topic.getThemes();
            themes.remove(this);
        }
    }

    public Theme(String name) {
        this.name = name;
    }
}
