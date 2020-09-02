package ru.javamentor.service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.theme.ThemeDAOImpl;
import ru.javamentor.model.Theme;
import ru.javamentor.service.theme.ThemeServiceImpl;
import java.util.ArrayList;
import static org.mockito.Mockito.doThrow;

/**
 * Тесты для класса ThemeServiceImpl
 *
 * @version 1.0
 * @author Java Mentor
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThemeServiceTest {

    Theme theme = new Theme("name");
    @Autowired
    ThemeServiceImpl themeService;

    @MockBean
    ThemeDAOImpl themeDAO;

    /**
     * Тест метода получения всех тем
     */
    @Test
    public void getAllThemes() {
        Mockito.doReturn(new ArrayList<Theme>())
                .when(themeDAO)//четко
                .getAllThemes();
        Assert.assertNull("проверка на null", themeService.getAllThemes());
        Mockito.verify(themeDAO, Mockito.times(1)).getAllThemes();
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetAllThemes() {
        doThrow(new RuntimeException())
                .when(themeDAO)
                .getAllThemes();
        Assert.assertNull("проверка на null", themeService.getAllThemes());
        Mockito.verify(themeDAO).getAllThemes();
    }

    /**
     * Тест метода добавления тем
     */

    @Test
    public void addTheme() {
        Theme theme = new Theme();
        Assert.assertTrue("проверка на состояние выполнения метода", themeService.addTheme(theme));
        Mockito.verify(themeDAO, Mockito.times(1)).addTheme(theme);
    }

    @Test
    public void failTestAddTheme() {
        Theme theme = new Theme();
        doThrow(new RuntimeException())
                .when(themeDAO)
                .addTheme(theme);
        Mockito.verify(themeDAO, Mockito.times(0)).addTheme(theme);
    }

    /**
     * Тест метода удаления тем
     */
    @Test
    public void deleteTheme() {
        Theme theme = new Theme();
        Assert.assertTrue("проверка на состояние выполнения метода", themeService.deleteTheme(theme.getId()));
        Mockito.verify(themeDAO, Mockito.times(1)).deleteTheme(theme.getId());
    }

    @Test
    public void failTestDeleteTheme() {
        Theme theme = new Theme();
        doThrow(new RuntimeException())
                .when(themeDAO)
                .deleteTheme(theme.getId());
        Mockito.verify(themeDAO, Mockito.times(0)).deleteTheme(theme.getId());
    }

    /**
     * Тест метода для изменения тем по их id у конкретного пользователя
     */
    @Test
    public void getById() {
        Mockito.doReturn(new ArrayList<Theme>())
                .when(themeDAO)
                .getThemesByIds(ArgumentMatchers.anySet());


        Assert.assertNotNull("проверка на null", themeService.getThemesByIds(ArgumentMatchers.anySet()));
        Mockito.verify(themeDAO, Mockito.times(1)).getThemesByIds(ArgumentMatchers.anySet());
    }

    @Test(expected = RuntimeException.class)
    public void failTestGetById() {
        Mockito.doThrow(new RuntimeException())
                .when(themeDAO)
                .getThemesByIds(ArgumentMatchers.anySet());

        Assert.assertNull("проверка на null", themeService.getThemesByIds(ArgumentMatchers.anySet()));
        Mockito.verify(themeDAO).getThemesByIds(ArgumentMatchers.anySet());
    }
}
