package ru.javamentor.dao.theme;

import ru.javamentor.model.Theme;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для доступа к темам из базы
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface ThemeDAO {

    /**
     * Метод для получения листа тем.
     */
    List<Theme> getAllThemes();

    /**
     * Метод для добавления темы.
     */
    boolean addTheme(Theme theme);

    /**
     * Метод для удаления темы.
     */
    boolean deleteTheme(Long id);

    /**
     * Метод для получения списка тем по id сэта.
     */
    List<Theme> getThemesByIds(Set<Long> idThemes);

    boolean isExist(Long themeId);
}
