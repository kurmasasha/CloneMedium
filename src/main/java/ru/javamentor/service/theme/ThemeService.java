package ru.javamentor.service.theme;

import ru.javamentor.model.Theme;
import org.springframework.ui.Model;
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

    /**
     * Метод для получения списка тем по их id
     *
     * @param idThemes - множество уникальных id разных тем
     * @return List - список тем
     */
    List<Theme> getThemesByIds(Set<Long> idThemes);

    /**
     * Метод для изменения тем по их id у конкретного пользователя
     *
     * @param themesIds - множество уникальных id разных тем
     * @param userDB - конкретный пользователь
     * @return void
     */
    void changeThemes(Set<Long> themesIds, User userDB);

    /**
     * Метод для отображения тем
     *
     * @param model - переменна для передачи тем на контроллер
     * @param userDB - конкретный пользователь
     * @return void
     */
    void showThemes(Model model, User userDB);
}
