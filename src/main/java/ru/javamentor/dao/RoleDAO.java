package ru.javamentor.dao;

import ru.javamentor.model.Role;

import java.util.List;

public interface RoleDAO {

    public void addRole(Role role);

    public Role getRoleById(Long id);

    public Role getRoleByName(String name);

    public void removeRole(Long id);

    public List<Role> getAllRoles();
}
