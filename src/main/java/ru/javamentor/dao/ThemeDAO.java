package ru.javamentor.dao;

import ru.javamentor.model.Theme;

import java.util.List;

public interface ThemeDAO {

    List<Theme> getAllThemes();

    boolean addTheme(Theme theme);

    boolean deleteTheme(Long id);
}
