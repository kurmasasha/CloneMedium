package ru.javamentor.service.theme;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.javamentor.dao.theme.ThemeDAO;
import ru.javamentor.model.Theme;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса ThemeService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Service
@Slf4j
public class ThemeServiceImpl implements ThemeService {

    private final ThemeDAO themeDAO;

    @Autowired
    public ThemeServiceImpl(ThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    /**
     * Метод получения всех тем
     * @return - список тем
     */
    @Transactional(readOnly = true)
    @Override
    public List<Theme> getAllThemes() {
        try {
            List<Theme> result = themeDAO.getAllThemes();
            log.debug("IN getAllThemes - {} themes found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllThemes in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Метод добавления темы
     * @param theme - класс темы для добавления в базу
     * @return - true, если тема была успешно добавлена
     *           false, в случае ошибки
     */
    @Transactional
    @Override
    public boolean addTheme(Theme theme) {
        try {
            log.debug("IN addTheme - theme id: {} theme name: {}", theme.getId(), theme.getName());
            themeDAO.addTheme(theme);
            return true;
        } catch (Exception e) {
            log.error("IN addTheme - theme not added with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Метод удаления темы
     * @param id - id темы в базе
     * @return - true, если тема была успешно удалена
     *           false, в случае ошибки
     */
    @Transactional
    @Override
    public boolean deleteTheme(Long id) {
        try {
            log.debug("IN deleteTheme - theme id: {} delete successful", id);
            themeDAO.deleteTheme(id);
            return true;
        } catch (Exception e) {
            log.error("Exception while deleteTheme in service with theme.id is {} not delete", id);
            throw new RuntimeException();
        }
    }

    /**
     * Метод для получения списка тем по их id
     *
     * @param idThemes - множество уникальных id разных тем
     * @return List - список тем
     */
    @Transactional
    @Override
    public List<Theme> getThemesByIds(Set<Long> idThemes) {
        try {
            List<Theme> result = themeDAO.getThemesByIds(idThemes);
            log.debug("IN getThemesByIds - {} themes found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getThemesByIds in service with themes.id is {} not found", idThemes);
            throw new RuntimeException();
        }
    }

    /**
     * Метод для изменения тем по их id у конкретного пользователя
     *
     * @param themesIds - множество уникальных id разных тем
     * @param userDB - конкретный пользователь
     * @return void
     */
    @Override
    public void changeThemes(Set<Long> themesIds, User userDB) {
        log.debug("IN changeThemes in service with userDB.id: {} and userDB.userName: {}",
                userDB.getId(), userDB.getUsername());
        Set<Theme> themesOfUser = userDB.getThemes();
        List<Theme> themeList = getThemesByIds(themesIds);
        if (themesOfUser.size() != themeList.size() || !themesOfUser.containsAll(themeList)) {
            if (themesIds != null) {
                if (themesOfUser.size() != 0) {
                    themesOfUser.clear();
                }
                themesOfUser.addAll(themeList);
            } else {
                themesOfUser.clear();
            }
        }
    }

    /**
     * Метод для отображения тем
     *
     * @param model - переменна для передачи тем на контроллер
     * @param userDB - конкретный пользователь
     * @return void
     */
    @Override
    public void showThemes(Model model, User userDB) {
        log.debug("IN showThemes in service with userDB.id: {} and userDB.userName: {}",
                userDB.getId(), userDB.getUsername());
        List<Theme> allThemes = getAllThemes(); // все темы
        if (userDB.getThemes().size() != 0) {
            Set<Theme> themesOfUser = userDB.getThemes(); // все темы юзера
            for (Theme themeOfUser : themesOfUser) {
                allThemes.remove(themeOfUser); //удаляем из всех тем темы юзера
            }
            model.addAttribute("themesOfUser", themesOfUser);
        }
        model.addAttribute("themes", allThemes);
    }

    @Override
    public boolean isExisting(Long themeId){
        return themeDAO.isExist(themeId);
    }

}
