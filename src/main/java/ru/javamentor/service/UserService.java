package ru.javamentor.service;

import org.springframework.security.core.GrantedAuthority;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {

    public boolean addUser(User user);

    public boolean addUserThroughSocialNetworks(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public boolean updateUser(User user);

    public void removeUser(Long id);

    boolean activateUser(String code);

    public User getUserByEmail(String email);

    User getUserByUsername(String username);

    void sendCode(User user);

    User findByActivationCode(String code);

    void login(String username, String password, Collection<? extends GrantedAuthority> authorities);
//    void login(String username, String password, Set<Role> roles);

}
