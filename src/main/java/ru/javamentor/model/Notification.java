package ru.javamentor.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс представляющий модель уведомления
 *
 * @version 1.0
 * @author Java Mentor
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "notifications")
public class Notification {

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
    private String text;

    @ManyToOne
    @JoinColumn
    private User user;


    public Notification(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
    }
}
