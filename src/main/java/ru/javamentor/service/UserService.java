package ru.javamentor.service;

import ru.javamentor.model.User;

import java.util.List;

public interface UserService {

    public boolean addUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public boolean updateUser(User user);

    public void removeUser(Long id);

    boolean activateUser(String code);

    public User getUserByEmail(String email);

    User getUserByUsername(String username);

    void sendCode(User user);

    User findByActivationCode(String code);

}
