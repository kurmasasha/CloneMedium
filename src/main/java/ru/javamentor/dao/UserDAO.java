package ru.javamentor.dao;

import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для доступа к пользователям из базы
 *
 * @version 1.0
 * @autor Java Mentor
 */
public interface UserDAO {
    /**
     * метод для добавления пользователя в базу
     *
     * @param user - объект представляющий пользователя в системе
     */
    void addUser(User user);

    /**
     * метод для получения всех пользователей из базы
     *
     * @return List пользователей
     */
    List<User> getAllUsers();

    /**
     * метод для получения всех пользователей связанных с топиком
     */
    List<User> getAllUsersByTopicId();

    /**
     * метод для получения пользователя по id
     *
     * @param id - уникальный id пользователя
     * @return User - объект представляющий пользователя
     */
    User getUserById(Long id);

    /**
     * метод для обновления пользователя
     *
     * @param user - объект обновленного пользователя
     * @return void
     */
    void updateUser(User user);

    /**
     * метод для удаления пользователя из базы
     *
     * @param id - уникальный id пользователя
     * @return void
     */
    void removeUser(Long id);

    /**
     * метод для получения пользователя по имени
     *
     * @param userName - email пользователя
     * @return User - объект представляющий пользователя
     */
    User getUserByUsername(String userName);

    /**
     * метод для получения пользователя его активационному коду
     *
     * @param code - активационный код
     * @return User - объект представляющий пользователя
     */
    User findByActivationCode(String code);
}
