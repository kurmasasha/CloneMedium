package ru.javamentor.dao;

import ru.javamentor.model.User;

import java.util.List;

public interface UserDAO {

    void addUser(User user);

    List<User> getAllUsers();

    List<User> getAllUsersByTopicId();

    User getUserById(Long id);

    void updateUser(User user);

    void removeUser(Long id);

    User getUserByUsername(String userName);

    User findByActivationCode(String code);
}
