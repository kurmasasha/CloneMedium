package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.ThemeDAO;
import ru.javamentor.dao.TopicDAO;
import ru.javamentor.model.Theme;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.time.LocalDateTime;
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
        List<Theme> result = themeDAO.getAllThemes();
        log.info("IN getAllThemes - {} themes found", result.size());
        return result;
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
        return themeDAO.addTheme(theme);
    }


    @Transactional
    @Override
    public boolean deleteTheme(Long id) {
        return themeDAO.deleteTheme(id);
    }
}
