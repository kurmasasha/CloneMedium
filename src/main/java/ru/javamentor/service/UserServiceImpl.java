package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        userDAO.addUser(user);
        return true;
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        userDAO.updateUser(user);
        return true;
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDAO.removeUser(id);
    }
}
