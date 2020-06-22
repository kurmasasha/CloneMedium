package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
/**
 * Класс реализующий интерфейс RoleDao для доступа к ролям из базы с помощью Hibernate
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Repository
public class RoleDAOImpl  implements RoleDAO{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * метод для добавления роли в базу
     * @param role - объект представляющий роль пользователя
     */
    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
    /**
     * метод для получения роли по id
     * @param id - уникальный id роли
     * @return объект представляющий роль пользователя
     */
    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    /**
     * метод для получения роли по имени
     *
     * @param name - наименование роли
     * @return объект представляющий роль пользователя
     */
    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name =:n", Role.class)
                .setParameter("n", name)
                .getSingleResult();
    }
    /**
     * метод для удаления
     * @param id - уникальный id роли
     * @return void
     */
    @Override
    public void removeRole(Long id) {
        entityManager.remove(getRoleById(id));
    }
    /**
     * метод для получения всех ролей из базы
     * @return List объектов ролей
     */
    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }
}
