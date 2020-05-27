package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

 /*   @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<User> currentUser = Optional.ofNullable(userDAO.getUserByUsername(userName));
        return currentUser.orElseThrow(IllegalArgumentException::new);
    }*/
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        User currentUser = userDAO.getUserByUsername(userName);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else {
            return currentUser;
        }
    }

}
