package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.javamentor.dao.ThemeDAO;
import ru.javamentor.model.Theme;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса ThemeService
 *
 * @author Java Mentor
 * @version 1.0
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
     *
     * @return - список тем
     */
    @Transactional(readOnly = true)
    @Override
    public List<Theme> getAllThemes() {
        List<Theme> result = themeDAO.getAllThemes();
        log.info("IN getAllThemes - {} themes found", result.size());
        return result;
    }

    @Transactional
    @Override
    public List<Theme> getThemesByIds(Set<Long> idThemes) {
            List<Theme> result = themeDAO.getThemesByIds(idThemes);
            log.info("IN getThemesByIds - {} themes found", result.size());
            return result;
    }

    @Override
    public void changeThemes(Set<Long> themesIds, User userDB) {
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

    @Override
    public void showThemes(Model model, User userDB) {
        List<Theme> allThemes = getAllThemes();
        if (userDB.getThemes().size() != 0) {
            Set<Theme> themesOfUser = userDB.getThemes();
            for (Theme themeOfUser : themesOfUser) {
                allThemes.remove(themeOfUser);
            }
            model.addAttribute("themesOfUser", themesOfUser);
        }
        model.addAttribute("themes", allThemes);
    }

}
