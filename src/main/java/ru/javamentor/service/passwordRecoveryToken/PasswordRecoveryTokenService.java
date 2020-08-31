package ru.javamentor.service.passwordRecoveryToken;

import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.User;

import java.util.List;

/**
 * Интерфейс для сервиса ключей восстановления пароля
 *
 * @author Java Mentor
 * @version 1.0
 */
public interface PasswordRecoveryTokenService {

    /**
     * метод для получения всех токенов
     */
    List<PasswordRecoveryToken> getAllPasswordRecoveryTokens();

    /**
     * метод для добавления токена в базу
     *
     * @param passwordRecoveryToken - токен
     */
    boolean addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * метод для удаления токена из базы
     *
     * @param passwordRecoveryToken - токен
     */
    boolean deletePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * метод для обновления токена в базе
     *
     * @param passwordRecoveryToken - токен
     */
    boolean updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * получение токена по id
     *
     * @param id - id токена
     */
    PasswordRecoveryToken getPasswordRecoveryTokenById(Long id);

    /**
     * получение токена по хэшу
     *
     * @param token - хэш, по которому ищем
     */
    PasswordRecoveryToken getPasswordRecoveryTokenByToken(String token);

    /**
     * отправление токена по мэйлу
     *
     * @param passwordRecoveryToken - токен для отправки
     */
    void sendPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * отправление токена по мэйлу
     *
     */
    boolean isValid(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * Метод генерации временного пароля
     */
    String generateTempPass();

    /**
     * отправление временного пароля пользователю
     *
     * @param user - кому отправляем
     * @param tempPass - временный пароль
     */
    void sendTempPass(User user, String tempPass);
}
