package ru.javamentor.dao.user;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Реализация интерфейса UserDaо с помощью Hibernate
 *
 * @version 1.0
 * @author Java Mentor
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * метод для добавления пользователя в базу
     *
     * @param user - объект представляющий пользователя в системе
     */
    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    /**
     * метод для получения всех пользователей из базы
     *
     * @return List пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role", User.class).getResultList();
    }

    /**
     * метод для получения всех пользователей связанных с топиком
     */
    @Override
    public List<User> getAllUsersByTopicId() {
        return null;//Написать реализацию
    }

    /**
     * метод для получения пользователя по id
     *
     * @param id - уникальный id пользователя
     * @return User - объект представляющий пользователя
     */
    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * метод для обновления пользователя
     *
     * @param user - объект обновленного пользователя
     */
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    /**
     * метод для удаления пользователя из базы
     *
     * @param id - уникальный id пользователя
     */
    @Override
    public void removeUser(Long id) {
        entityManager.remove(getUserById(id));
    }

    /**
     * метод для получения пользователя по имени
     *
     * @param userName - email пользователя
     * @return User - объект представляющий пользователя
     */
    @Override
    public User getUserByUsername(String userName) {
        try {
            return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role WHERE u.username =:username", User.class)
                    .setParameter("username", userName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * метод для получения пользователя по его активационному коду
     *
     * @param code - активационный код
     * @return User - объект представляющий пользователя
     */
    @Override
    public User findByActivationCode(String code) {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role WHERE u.activationCode =:code", User.class)
                .setParameter("code", code)
                .getSingleResult();
    }

    /**
     * метод для получения пользователя по его электронной почте
     *
     * @param email - электронная почта пользователя
     * @return User - объект представляющий пользователя
     */

    public User getUserByEmail(String email) {
        try {
            return entityManager.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Метод получения списка всех имен авторов не связанных с пользователем (ников)
     * @param username - имя пользователя
     * @return - список имен авторов (ников)
     */
    @Override
    public List<String> getAllSubscribesNotOfUser(String username) {
        return entityManager.createQuery(
                "SELECT u.username FROM User u " +
                        "WHERE u.username != :username " +
                        "AND u.username NOT IN " +
                        "(SELECT s.author.username FROM Subscribes s " +
                        "WHERE s.subscriber.username = :username)",
                String.class)
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * Метод получения списка подписок пользователя
     * @param username - имя пользователя
     * @return - список подписок
     */
    @Override
    public List<String> getAllSubscribesOfUser(String username) {
        return entityManager.createQuery(
                "SELECT s.author.username FROM Subscribes s " +
                        "WHERE s.subscriber.username = :username",
                String.class)
                .setParameter("username", username)
                .getResultList();
    }

    /**
     * Метод получения всех подписчиков автора
     * @param author - автор
     * @return - список подписчиков
     */
    @Override
    public List<User> getAllSubscribersOfAuthor(String author) {
        return entityManager.createQuery(
                "SELECT s.subscriber FROM Subscribes s " +
                        "WHERE s.author.username = :author",
                User.class)
                .setParameter("author", author)
                .getResultList();
    }

    /**
     * Метод добавления подписки
     * @param author - автор
     * @param subscriber - подписчик
     */
    @Override
    public void addSubscribe(String author, String subscriber) {
        entityManager.createNativeQuery(
                "INSERT INTO subscribes(author_id, subscriber_id) " +
                        "VALUES (" +
                            "(SELECT id FROM users " +
                             "WHERE users.username = :author), " +
                            "(SELECT id FROM users " +
                             "WHERE users.username = :subscriber))")
                .setParameter("author", author)
                .setParameter("subscriber", subscriber)
                .executeUpdate();
    }

    /**
     * Метод удаления подписок пользователя
     * @param subscriber - подписчик
     */
    @Override
    public void deleteSubscribesOfUser(String subscriber) {
        entityManager.createNativeQuery(
                "DELETE FROM Subscribes " +
                        "WHERE subscriber_id = " +
                            "(SELECT id FROM users " +
                             "WHERE users.username = :subscriber)")
                .setParameter("subscriber", subscriber)
                .executeUpdate();
    }
}