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


    @Column(name = "is_read")
    boolean readBy;

    public Notification(String title, String text, User user, boolean readBy) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.readBy = readBy;
    }
}
