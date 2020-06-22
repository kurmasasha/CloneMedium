package ru.javamentor.service;

import ru.javamentor.model.Role;

import java.util.List;

/**
 * Интерфейс для работы с ролями пользователей
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface RoleService {
    /**
     * метод для добавления роли
     *
     * @param role - добавляемая роль
     * @return boolean - удалось добавить роль или нет
     */
    public boolean addRole(Role role);

    /**
     * метод для получения роли по id
     *
     * @param id - уникальный id роли
     * @return Role - объект представляющий роль пользователей
     */
    public Role getRoleById(Long id);

    /**
     * метод для получения роли по названию
     *
     * @param name - название роли
     * @return Role - объект представляющий роль пользователей
     */
    public Role getRoleByName(String name);

    /**
     * метод для удаления роли по id
     *
     * @param id - уникальный id роли
     * @return void
     */
    public void removeRole(Long id);

    /**
     * метод для получения списка всех ролей
     *
     * @return List ролей
     */
    public List<Role> getAllRoles();
}
