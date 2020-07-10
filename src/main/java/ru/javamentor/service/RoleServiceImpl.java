package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.RoleDAO;
import ru.javamentor.model.Role;

import java.util.List;

/**
 * Реализация интерфейса RoleService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    /**
     * метод для добавления роли
     *
     * @param role - добавляемая роль
     * @return boolean - удалось добавить роль или нет
     */
    @Transactional
    @Override
    public boolean addRole(Role role) {
        roleDAO.addRole(role);
        return true;
    }

    /**
     * метод для получения роли по id
     *
     * @param id - уникальный id роли
     * @return Role - объект представляющий роль пользователей
     */
    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleDAO.getRoleById(id);
    }

    /**
     * метод для получения роли по названию
     *
     * @param name - название роли
     * @return Role - объект представляющий роль пользователей
     */
    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    /**
     * метод для удаления роли по id
     *
     * @param id - уникальный id роли
     * @return void
     */
    @Transactional
    @Override
    public void removeRole(Long id) {
        roleDAO.removeRole(id);
    }

    /**
     * метод для получения списка всех ролей
     *
     * @return List ролей
     */
    @Transactional
    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}
