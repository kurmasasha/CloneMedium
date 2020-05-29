package ru.javamentor.dao;

import ru.javamentor.model.Role;

import java.util.List;

public interface RoleDAO {

    void addRole(Role role);

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    void removeRole(Long id);

    List<Role> getAllRoles();
}
