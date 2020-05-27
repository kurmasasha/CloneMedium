package ru.javamentor.service;

import ru.javamentor.model.Role;
import ru.javamentor.model.User;

import java.util.List;

public interface RoleService {

    public boolean addRole(Role role);

    public Role getRoleById(Long id);

    public Role getRoleByName(String name);

    public void removeRole(Long id);

    public List<Role> getAllRoles();
}
