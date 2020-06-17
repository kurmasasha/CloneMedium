package ru.javamentor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.javamentor.dao.UserDAO;
import ru.javamentor.model.Topic;
import ru.javamentor.model.User;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

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
        user.setActivated(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDAO.addUser(user);

        if(!StringUtils.isEmpty(user.getUsername())) {
            sendCode(user);
        }
        return true;
    }

    @Transactional
    @Override
    public void sendCode(User user) {
        String message = String.format(
                "Hello, %s \n" +
                        "Welcome to CloneMedium. Please visit next link for confirm email: <a target=\"_blank\" href=" + "%s" +
                        "registration/activate/%s" + ">Confirm</a>",
                user.getUsername(),
                link,
                user.getActivationCode()
        );
        mailSender.send(user.getUsername(), "Activation code", message);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        List<User> result = userDAO.getAllUsers();
        log.info("IN getAllUsers - {} users found", result.size());
        return result;
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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
        user.setActivated(true);
        userDAO.updateUser(user);

        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByUsername(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

}
