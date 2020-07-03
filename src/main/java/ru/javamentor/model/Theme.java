
package ru.javamentor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс представляющий модель темы топика
 *
 * @version 1.0
 * @author Java Mentor
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    @NotNull
    private String name;

    public Theme(String name) {
        this.name = name;
    }
}

