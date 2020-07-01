package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
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
}
