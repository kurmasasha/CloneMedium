package ru.javamentor.dao.theme;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Theme;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Repository
public class ThemeDAOImpl implements ThemeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Theme> getAllThemes() {
        return entityManager.createQuery(
                "SELECT t FROM Theme t " +
                        "ORDER BY t.name  asc",
                Theme.class)
                .getResultList();
    }

    @Override
    public boolean addTheme(Theme theme) {
        try {
            entityManager.persist(theme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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
    public boolean isExist(Long themeId) {
        Query count = entityManager.createQuery("SELECT COUNT(t.id) FROM Theme t WHERE t.id = :themeId")
                .setParameter("themeId", themeId);
        long res = Long.parseLong(String.valueOf(count));
        return res > 0;
    }

}
