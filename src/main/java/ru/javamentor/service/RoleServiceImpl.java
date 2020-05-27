package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.RoleDAO;
import ru.javamentor.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO){
        this.roleDAO = roleDAO;
    }

    @Transactional
    @Override
    public boolean addRole(Role role) {
        roleDAO.addRole(role);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long id) {
        return roleDAO.getRoleById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    @Transactional
    @Override
    public void removeRole(Long id) {
        roleDAO.removeRole(id);
    }

    @Transactional
    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}
