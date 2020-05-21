package ru.javamentor.dao;

import ru.javamentor.model.Role;

public interface RoleDAO {

    public void addRole(String role);

    public Role getRoleById(Long id);

    public Role getRoleByName(String name);
}
