package ru.javamentor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс представляющий DTO модель пользователя для передачи в JSON для живого поиска select2multiple
 *
 * @version 1.0
 * @autor Java Mentor
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String text;
}
