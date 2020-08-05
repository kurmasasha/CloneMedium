package ru.javamentor.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс для работы с пользователями в системе
 *
 * @version 1.0
 * @author Java Mentor
 */
public interface UserService {
    /**
     * метод для добавления пользователя
     *
     * @param user - пользователь которого необходимо добавить
     * @return boolean удалось добавить или нет
     */
    boolean addUser(User user);

    /**
     * метод для добавления пользователя из социальной сети
     *
     * @param user - пользователь которого необходимо добавить
     * @return boolean удалось добавить или нет
     */
    boolean addUserThroughSocialNetworks(User user);

    /**
     * метод для получения всего списка пользователей
     *
     * @return List пользоватей
     */
    List<User> getAllUsers();

    /**
     * метод для получения пользователя по уникальному id
     *
     * @param id - уникальный id пользователя
     * @return User - пользователь
     */
    User getUserById(Long id);

    /**
     * метод для обновления пользовательских данных
     *
     * @param user - пользователь с обновленными данными
     * @return boolean - удалось обновить данные или нет
     */
    boolean updateUser(User user);

    /**
     * метод для удаления пользователя
     *
     * @param id - уникальный id пользователя в системе
     */
    void removeUser(Long id);

    /**
     * метод для активации пользователя в системе
     *
     * @param code - активационный код пользователя
     * @return boolean - удалось активировать пользователя или нет
     */
    boolean activateUser(String code);

    /**
     * метод для получения пользователя по его электронной почте
     *
     * @param email -  электронная почта пользователя
     * @return User пользователь
     */
    User getUserByEmail(String email);

    /**
     * метод для получения пользователя по его username
     *
     * @param username - имя пользователя
     * @return User пользователь
     */
    User getUserByUsername(String username);

    /**
     * метод для отправки письма подтверждения на почту
     *
     * @param user - пользователь которому нужно отправить письмо
     */
    void sendCode(User user);

    /**
     * метод для отправки получения пользователя по его активационному коду
     *
     * @param code - строковое представление активационного кода
     * @return User - пользователь
     */
    User findByActivationCode(String code);

    /**
     * метод для входа пользователя в систему by Spring Security
     *
     * @param username    - логие
     * @param password    - пароль
     * @param authorities - роли by Spring Security
     */
    void login(String username, String password, Collection<? extends GrantedAuthority> authorities);

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
     * Метод добавления подписки
     * @param authors - автор
     * @param subscriber - подписчик
     * @return - успех операции добавления
     */
    boolean changeSubscribe(Set<String> authors, String subscriber);

    /**
     * Метод создания уведомлений для всех подписчиков автора
     * @param author - автор
     * @return - успех операции создания уведомлений
     */
    boolean notifyAllSubscribersOfAuthor(String author, String title, String text);

    @Transactional
    boolean isExist(Long commentId);
}
