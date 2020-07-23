package ru.javamentor.service.passwordRecoveryToken;

import ru.javamentor.model.PasswordRecoveryToken;

import java.util.List;

/**
 * Класс для сервис ключам восстанволения пароля
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
     * @param passwordRecoveryToken - токен
     */
    boolean addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * метод для обновления токена в базе
     * @param passwordRecoveryToken - токен
     */
    boolean updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * получение токена по id
     * @param id - id токена
     */
    PasswordRecoveryToken getPasswordRecoveryTokenById(Long id);

    /**
     * получение токена по хэшу
     * @param hashMail - хэш, по которому ищем
     */
    PasswordRecoveryToken getPasswordRecoveryTokenByHash(String hashMail);

    /**
     * отправление токена по мэйлу
     *
     * @param passwordRecoveryToken - токен для отправки
     */
    void sendPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);
}
