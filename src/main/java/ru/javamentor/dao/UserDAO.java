package ru.javamentor.dao;

import ru.javamentor.model.User;

import java.util.List;

public interface UserDAO {

    public void addUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public void updateUser(User user);

    public void removeUser(Long id);

    User getUserByUsername(String userName);
}
