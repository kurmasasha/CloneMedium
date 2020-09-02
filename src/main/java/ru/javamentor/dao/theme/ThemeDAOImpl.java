package ru.javamentor.dao.theme;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Theme;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса ThemeDAO
 *
 * @version 1.0
 * @author Java Mentor
 */

@Repository
public class ThemeDAOImpl implements ThemeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Метод для получения листа тем.
     */
    @Override
    public List<Theme> getAllThemes() {
        return entityManager.createQuery(
                "SELECT t FROM Theme t " +
                        "ORDER BY t.name  asc",
                Theme.class)
                .getResultList();
    }

    /**
     * Метод для добавления темы.
     */
    @Override
    public boolean addTheme(Theme theme) {
        try {
            entityManager.persist(theme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для удаления темы.
     */
    @Override
    public boolean deleteTheme(Long id) {
        try {
            Theme theme = entityManager.find(Theme.class, id);
            if (theme != null) {
                entityManager.remove(theme);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для получения списка тем по id сэта.
     */
    @Override
    public List<Theme> getThemesByIds(Set<Long> idThemes) {
        return entityManager.createQuery(
                "SELECT t FROM Theme t " +
                        "WHERE t.id IN :value " +
                        "ORDER BY t.name ASC", Theme.class)
                .setParameter("value", idThemes)
                .getResultList();
    }

    @Override
    public boolean isExist(Long themeId){
        Long count = entityManager.createQuery("SELECT COUNT(t.id) FROM Theme t WHERE t.id = :themeId", Long.class)
                .setParameter("themeId", themeId)
                .getSingleResult();
        return count>0;
    }

}
