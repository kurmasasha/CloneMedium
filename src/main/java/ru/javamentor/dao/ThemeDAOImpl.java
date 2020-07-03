package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.model.Theme;
import ru.javamentor.model.Topic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
}
