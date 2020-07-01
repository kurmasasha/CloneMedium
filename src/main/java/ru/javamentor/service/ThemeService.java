package ru.javamentor.service;

import ru.javamentor.model.Theme;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для работы с темами
 *
 * @version 1.0
 * @author Java Mentor
 */
public interface ThemeService {
    /**
     * Метод получения всех тем
     * @return - список тем
     */
    List<Theme> getAllThemes();
}
