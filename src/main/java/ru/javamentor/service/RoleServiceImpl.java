package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.RoleDAO;
import ru.javamentor.model.Role;

import java.util.List;
import java.util.Objects;

/**
 * Реализация интерфейса RoleService
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Slf4j
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
        try {
            roleDAO.addRole(role);
            log.debug("IN addRole - role.name: {}", role.getName());
            return true;
        } catch (Exception e) {
            log.error("IN addRole - role not added with exception {}", e.getMessage());
            throw new RuntimeException();
        }
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
        try {
            var role = roleDAO.getRoleById(id);
            log.debug("IN getRoleById - role.id: {} and role.name: {}", id, role.getName());
            return role;
        } catch (Exception e) {
            log.error("Exception while getRoleById in service with role.id is {}", id);
            throw new RuntimeException();
        }
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
        try {
            var role = roleDAO.getRoleByName(name);
            log.debug("IN getRoleByName - role.id: {} and role.name: {}", role.getId(), name);
            return role;
        } catch (Exception e) {
            log.error("Exception while getRoleByName in service with role.name is {}", name);
            throw new RuntimeException();
        }
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
        try {
            roleDAO.removeRole(id);
            log.debug("IN removeRole - role.id: {} delete successful", id);
        } catch (Exception e) {
            log.error("Exception while removeRole in service with role.id is {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения списка всех ролей
     *
     * @return List ролей
     */
    @Transactional
    @Override
    public List<Role> getAllRoles() {
        try {
            List<Role> result = roleDAO.getAllRoles();
            log.debug("IN getAllRoles - {} role found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllRoles in service not found with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
