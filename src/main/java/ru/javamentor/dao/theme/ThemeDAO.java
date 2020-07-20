package ru.javamentor.dao.theme;

import ru.javamentor.model.Theme;

import java.util.List;
import java.util.Set;

public interface ThemeDAO {

    List<Theme> getAllThemes();

    boolean addTheme(Theme theme);

    boolean deleteTheme(Long id);
    List<Theme> getThemesByIds(Set<Long> idThemes);
}
