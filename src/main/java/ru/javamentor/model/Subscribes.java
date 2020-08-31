package ru.javamentor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представляющий модель подписок
 *
 * @version 1.0
 * @author Java Mentor
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "subscribes")
public class Subscribes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User subscriber;

    @ManyToOne
    private User author;
}
