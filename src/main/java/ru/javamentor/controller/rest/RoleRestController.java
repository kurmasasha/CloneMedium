package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;

import java.util.List;

/**
 * Rest контроллер для ролей
 *
 * @version 1.0
 * @autor Java Mentor
 */
@RestController
@RequestMapping("/api")
public class RoleRestController {

    public RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * метод получения всех ролей
     *
     * @return ResponseEntity, который содержит List ролей
     */
    @GetMapping("/admin/allRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    /**
     * метод получения роли по ее имени
     *
     * @param name - имя роли
     * @return ResponseEntity, который содержит роль
     */
    @GetMapping("/admin/role")
    public ResponseEntity<Role> getRoleByName(String name) {
        return new ResponseEntity<>(roleService.getRoleByName(name), HttpStatus.OK);
    }

    /**
     * метод для добавления роли
     *
     * @param role - имя роли
     * @return ResponseEntity, который содержит статус OK
     */
    @PostMapping("/admin/addRole")
    public ResponseEntity<Role> addRole(String role) {
        roleService.addRole(new Role(role));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * метод для удаления роли
     *
     * @param id - уникальный id роли
     * @return ResponseEntity, который содержит статус ОК
     */
    @DeleteMapping("/admin/removeRole/{id}")
    public ResponseEntity<User> removeRole(@PathVariable Long id) {
        roleService.removeRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
