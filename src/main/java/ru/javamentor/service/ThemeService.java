package ru.javamentor.service;

import org.springframework.ui.Model;
import ru.javamentor.model.Theme;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для работы с темами
 *
 * @author Java Mentor
 * @version 1.0
 */
public interface ThemeService {
    /**
     * Метод получения всех тем
     *
     * @return - список тем
     */
    List<Theme> getAllThemes();

    List<Theme> getThemesByIds(Set<Long> idThemes);

    void changeThemes(Set<Long> themesIds, User userDB);

    void showThemes(Model model, User userDB);


}
