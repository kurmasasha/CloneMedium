package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserDetailServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
