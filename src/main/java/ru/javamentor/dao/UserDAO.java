package ru.javamentor.dao;

import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для доступа к пользователям из базы
 *
 * @version 1.0
 * @author Java Mentor
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
     */
    void updateUser(User user);

    /**
     * метод для удаления пользователя из базы
     *
     * @param id - уникальный id пользователя
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

    /**
     * Метод получения списка всех имен авторов не связанных с пользователем (ников)
     * @param username - имя пользователя
     * @return - список имен авторов (ников)
     */
    List<String> getAllSubscribesNotOfUser(String username);

    /**
     * Метод получения списка подписок пользователя
     * @param username - имя пользователя
     * @return - список подписок
     */
    List<String> getAllSubscribesOfUser(String username);

    /**
     * Метод получения всех подписчиков автора
     * @param author - автор
     * @return - список подписчиков
     */
    List<User> getAllSubscribersOfAuthor(String author);

    /**
     * Метод добавления подписки
     * @param author - автор
     * @param subscriber - подписчик
     */
    void addSubscribe(String author, String subscriber);

    /**
     * Метод удаления подписи
     * @param subscriber - подписчик
     */
    void deleteSubscribesOfUser(String subscriber);
}
