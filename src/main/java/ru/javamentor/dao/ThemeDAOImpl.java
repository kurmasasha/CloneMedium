package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Theme;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Theme> getThemesByIds(Set<Long> idThemes) {
        return entityManager.createQuery(
                "SELECT t FROM Theme t " +
                        "WHERE t.id IN :value " +
                        "ORDER BY t.name ASC", Theme.class)
                .setParameter("value", idThemes)
                .getResultList();
    }
}
