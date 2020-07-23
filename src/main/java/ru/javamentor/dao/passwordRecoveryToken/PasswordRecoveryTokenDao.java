package ru.javamentor.dao.passwordRecoveryToken;

import ru.javamentor.model.PasswordRecoveryToken;

import java.util.List;

/**
 * Класс для доступа к ключам восстанволения пароля
 *
 * @author Java Mentor
 * @version 1.0
 */
public interface PasswordRecoveryTokenDao {
    /**
     * метод для получения всех токенаов
     */
    List<PasswordRecoveryToken> getAllPasswordRecoveryTokens();

    /**
     * метод для добавления токена в базу
     * @param passwordRecoveryToken - токен
     */
    void addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * метод для обновления токена в базе
     * @param passwordRecoveryToken - токен
     */
    void updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);


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
}
