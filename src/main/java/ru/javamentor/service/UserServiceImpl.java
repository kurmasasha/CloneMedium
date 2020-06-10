package ru.javamentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.User;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDAO userDAO;
    private MailSender mailSender;

    @Value("${site.link}")
    private String link;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, MailSender mailSender) {
        this.userDAO = userDAO;
        this.mailSender = mailSender;
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        user.setActivationCode(UUID.randomUUID().toString());
        userDAO.addUser(user);

        if(!StringUtils.isEmpty(user.getUsername())) {
            String message = String.format(
                    "Hello, %s \n" +
                            "Welcome to CloneMedium. Please visit next link for confirm email: %s"+
                    "activate/%s",
                    user.getUsername(),
                    link,
                    user.getActivationCode()
            );
            mailSender.send(user.getUsername(), "Activation code", message);
        }
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


    @Transactional
    @Override
    public boolean activateUser(String code) {
        User user = userDAO.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userDAO.updateUser(user);

        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByUsername(email);
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
