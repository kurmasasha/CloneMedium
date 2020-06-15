package ru.javamentor.dao;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role", User.class).getResultList();
    }

    @Override
    public List<User> getAllUsersByTopicId() {
        return null;//Написать реализацию
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUser(Long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserByUsername(String userName) {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role WHERE u.username =:username", User.class)
                .setParameter("username", userName)
                .getSingleResult();
    }

    @Override
    public User findByActivationCode(String code) {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role WHERE u.activationCode =:code", User.class)
                .setParameter("code", code)
                .getSingleResult();
    }


    public User getUserByEmail(String email) {
        try {
            return entityManager.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}