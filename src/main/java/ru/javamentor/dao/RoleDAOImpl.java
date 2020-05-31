package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl  implements RoleDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name =:n", Role.class)
                .setParameter("n", name)
                .getSingleResult();
    }

    @Override
    public void removeRole(Long id) {
        entityManager.remove(getRoleById(id));
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }
}
