package ru.javamentor.dao;


import ru.javamentor.model.Theme;

import java.util.List;
import java.util.Set;

public interface ThemeDAO {

    List<Theme> getAllThemes();

    List<Theme> getThemesByIds(Set<Long> idThemes);
}
