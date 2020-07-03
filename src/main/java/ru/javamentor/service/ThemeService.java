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

    /**
     * Метод добавления темы
     * @param theme - класс темы для добавления в базу
     * @return - true, если тема была успешно добавлена
     *           false, в случае ошибки
     */
    boolean addTheme(Theme theme);

    /**
     * Метод удаления темы
     * @param id - id темы в базе
     * @return - true, если тема была успешно удалена
     *           false, в случае ошибки
     */
    boolean deleteTheme(Long id);
}
