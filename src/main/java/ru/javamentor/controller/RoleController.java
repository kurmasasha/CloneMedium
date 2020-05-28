package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    public RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin/allRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<> (roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/admin/role")
    public ResponseEntity<Role> getRoleByName(String name) {
        return new ResponseEntity<> (roleService.getRoleByName(name), HttpStatus.OK);
    }

    @PostMapping("/admin/addRole")
    public ResponseEntity<Role> addRole(String role) {
        roleService.addRole(new Role(role));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/admin/removeRole/{id}")
    public ResponseEntity<User> removeRole(@PathVariable Long id) {
        roleService.removeRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
