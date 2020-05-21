package ru.javamentor.service;

import ru.javamentor.model.Role;

public interface RoleService {

    public void addRole(String role);

    public Role getRoleById(Long id);

    public Role getRoleByName(String name);


}
