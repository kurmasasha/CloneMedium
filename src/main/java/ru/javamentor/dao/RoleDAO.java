package ru.javamentor.dao;

import ru.javamentor.model.Role;

import java.util.List;

/**
 * Интерфейс для доступа к ролям из базы
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface RoleDAO {
    /**
     * метод для добавления роли в базу
     * @param role - объект представляющий роль пользователя
     */
    void addRole(Role role);

    /**
     * метод для получения роли по id
     * @param id - уникальный id роли
     * @return объект представляющий роль пользователя
     */
    Role getRoleById(Long id);
    /**
     * метод для получения роли по имени
     * @param name - наименование роли
     * @return объект представляющий роль пользователя
     */
    Role getRoleByName(String name);
    /**
     * метод для удаления
     * @param id - уникальный id роли
     * @return void
     */
    void removeRole(Long id);

    /**
     * метод для получения всех ролей из базы
     * @return List объектов ролей
     */
    List<Role> getAllRoles();
}
