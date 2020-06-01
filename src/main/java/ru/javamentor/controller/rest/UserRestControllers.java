package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Role;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;
import ru.javamentor.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestControllers {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public UserRestControllers(UserService userService, RoleService roleService)
    {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<> (userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/admin/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/remove/{id}")
    public ResponseEntity<User> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<User> updateUser(Long id, String firstName, String lastName, String username, String password, String role) {
        Role userRole = roleService.getRoleByName(role);
        userService.updateUser(new User(firstName, lastName, username, password, userRole));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
