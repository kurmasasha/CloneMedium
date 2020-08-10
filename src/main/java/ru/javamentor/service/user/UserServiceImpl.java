package ru.javamentor.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.javamentor.dao.notification.NotificationDao;
import ru.javamentor.dao.user.UserDAO;
import ru.javamentor.model.Notification;
import ru.javamentor.model.User;
import ru.javamentor.service.mailSender.MailSender;

import java.util.*;

/**
 * Реализация интерфейса UserService
 *
 * @version 1.0
 * @author Java Mentor
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private MailSender mailSender;
    private NotificationDao notificationDao;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Value("${site.link}")
    private String link;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, MailSender mailSender, NotificationDao notificationDao, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userDAO = userDAO;
        this.mailSender = mailSender;
        this.notificationDao = notificationDao;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    /**
     * метод для добавления пользователя
     *
     * @param user - пользователь которого необходимо добавить
     * @return boolean удалось добавить или нет
     */
    @Transactional
    @Override
    public boolean addUser(User user) {
        user.setActivated(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
        if (!StringUtils.isEmpty(user.getUsername())) {
            sendCode(user);
        }
        log.debug("IN addUser - user.userName: {} successfully added", user.getUsername());
        return true;
    }

    /**
     * метод для добавления пользователя из социальной сети
     *
     * @param user - пользователь которого необходимо добавить
     * @return boolean удалось добавить или нет
     */
    @Transactional
    @Override
    public boolean addUserThroughSocialNetworks(User user) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userDAO.addUser(user);
            log.debug("IN addUserThroughSocialNetworks - user.userName: {} successfully added", user.getUsername());
            return true;
        } catch (Exception e) {
            log.error("IN addUserThroughSocialNetworks - user not added with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * метод для отправки письма подтверждения на почту
     *
     * @param user - пользователь которому нужно отправить письмо
     */
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
        log.debug("IN sendCode - to user.id is {} and user.userName is {} activation code is {}",
                user.getId(), user.getUsername(), user.getActivationCode());
    }

    /**
     * метод для отправки получения пользователя по его активационному коду
     *
     * @param code - строковое представление активационного кода
     * @return User - пользователь
     */
    @Transactional
    @Override
    public User findByActivationCode(String code) {
        try {
            User user = userDAO.findByActivationCode(code);
            log.debug("IN findByActivationCode - user.id is {} and user.userName is {}", user.getId(), user.getUsername());
            return user;
        } catch (Exception e) {
            log.error("Exception while findByActivationCode in service with activate code is {}", code);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения пользователя по уникальному id
     *
     * @param id - уникальный id пользователя
     * @return User - пользователь
     */
    @Transactional
    @Override
    public User getUserById(Long id) {
        try {
            User result = userDAO.getUserById(id);
            log.debug("IN getUserById - user.id: {} and user.userName: {}", id, result.getUsername());
            return result;
        } catch (Exception e) {
            log.error("Exception while getUserById in service with user.id is {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения всего списка пользователей
     *
     * @return List пользоватей
     */
    @Transactional
    @Override
    public List<User> getAllUsers() {
        try {
            List<User> result = userDAO.getAllUsers();
            log.debug("IN getAllUsers - {} users found", result.size());
            return result;
        } catch (Exception e) {
            log.error("Exception while getAllUsers in service with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения всего списка пользователей
     *
     * @return List пользоватей
     */
    @Transactional
    @Override
    public boolean updateUser(User user) {
        try {
            boolean isBCryptPassword = user.getPassword().length() < 45;
            if (isBCryptPassword) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userDAO.updateUser(user);
            log.debug("IN updateUser - user.id: {} user.name: {}", user.getId(), user.getUsername());
            return true;
        } catch (Exception e) {
            log.error("Exception while updateUser in service - user.id: {} user.name: {}", user.getId(), user.getUsername());
            throw new RuntimeException();
        }
    }

    /**
     * метод для удаления пользователя
     *
     * @param id - уникальный id пользователя в системе
     */
    @Transactional
    @Override
    public void removeUser(Long id) {
        try {
            userDAO.removeUser(id);
            log.debug("IN removeUser - user.id: {}", id);
        } catch (Exception e) {
            log.error("Exception while removeUser in service with user.id is {}", id);
            throw new RuntimeException();
        }
    }

    /**
     * метод для активации пользователя в системе
     *
     * @param code - активационный код пользователя
     * @return boolean - удалось активировать пользователя или нет
     */
    @Transactional
    @Override
    public boolean activateUser(String code) {
        User user = userDAO.findByActivationCode(code);
        if (user == null) {
            log.error("Exception while activateUser in service - not activate with code is {}", code);
            return false;
        }
        user.setActivationCode(null);
        user.setActivated(true);
        userDAO.updateUser(user);
        log.debug("IN activateUser - user.id is {} and user.userName is {}", user.getId(), user.getUsername());
        return true;
    }

    /**
     * метод для получения пользователя по его электронной почте
     *
     * @param email -  электронная почта пользователя
     * @return User пользователь
     */
    @Override
    public User getUserByEmail(String email) {
        try {
            User result = userDAO.getUserByUsername(email);
            log.debug("IN getUserByEmail - user.userName is {}", email);
            return result;
        } catch (Exception e) {
            log.error("Exception while getUserByEmail in service with user.email is {}", email);
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения пользователя по его username
     *
     * @param username - имя пользователя
     * @return User пользователь
     */
    @Override
    public User getUserByUsername(String username) {
        try {
            User result = userDAO.getUserByUsername(username);
            log.debug("IN getUserByUsername - user.id is {} and user.userName is {} and user.username: {}",
                    result.getId(), result.getUsername(), username);
            return result;
        } catch (Exception e) {
            log.error("Exception while getUserByUsername in service with user.username is {}", username);
            throw new RuntimeException();
        }
    }

    /**
     * метод для входа пользователя в систему by Spring Security
     *
     * @param username - логие
     * @param password - пароль
     * @param authorities - роли by Spring Security
     */
    @Override
    public void login(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authReq);
        log.debug("IN login User - username: {}", username);
    }

    /**
     * Метод получения списка всех имен авторов не связанных с пользователем (ников)
     * @param username - имя пользователя
     * @return - список имен авторов (ников)
     */
    @Override
    public List<String> getAllSubscribesNotOfUser(String username) {
        List<String> authors;
        try {
            authors =  userDAO.getAllSubscribesNotOfUser(username);
            log.debug("IN getAllSubscribesNotOfUser - {} authors found", authors.size());
            return authors;
        } catch (Exception e) {
            log.error("Exception while getAllSubscribesNotOfUser in service with user.username is {}", username);
            throw new RuntimeException();
        }
    }

    /**
     * Метод получения списка подписок пользователя
     * @param username - имя пользователя
     * @return - список подписок
     */
    @Override
    public List<String> getAllSubscribesOfUser(String username) {
        List<String> subcribes;
        try {
            subcribes =  userDAO.getAllSubscribesOfUser(username);
            log.debug("IN getAllSubscribesOfUser - {} authors found", subcribes.size());
            return subcribes;
        } catch (Exception e) {
            log.error("Exception while getAllSubscribesOfUser in service with user.username is {}", username);
            throw new RuntimeException();
        }
    }

    /**
     * Метод добавления подписок
     * @param authors - авторы
     * @param subscriber - подписчик
     */
    @Transactional
    @Override
    public boolean changeSubscribe(Set<String> authors, String subscriber) {
        try {
            userDAO.deleteSubscribesOfUser(subscriber);
            for (String author : authors) {
                userDAO.addSubscribe(author, subscriber);
                log.debug("Subscribe with author: " + author + " and subscriber: " + subscriber + "successful");
            }
            return true;
        } catch (Exception e) {
            for (String author : authors) {
                log.error("Exception while changeSubscribe in service with author: " + author
                        + " and subscriber: " + subscriber + "successful");
            }
            return false;
        }
    }

    /**
     * Метод создания уведомлений для всех подписчиков автора
     * @param author - автор
     */
    @Transactional
    @Override
    public boolean notifyAllSubscribersOfAuthor(String author, String title, String text) {
        try {
            List<User> subscribers = userDAO.getAllSubscribersOfAuthor(author);
            for (User subscriber : subscribers) {
                notificationDao.addNotification(new Notification(title, text, subscriber));
                log.debug("IN notifyAllSubscribersOfAuthor notification for {} is added", subscriber.getUsername());
            }
            return true;
        } catch (Exception e) {
            log.error("Exception while notifyAllSubscribersOfAuthor in service with author is {} and title is {}", author, title);
            return false;
        }
    }

    @Override
    public boolean isExist(Long userId){
        return userDAO.isExist(userId);
    }

}
