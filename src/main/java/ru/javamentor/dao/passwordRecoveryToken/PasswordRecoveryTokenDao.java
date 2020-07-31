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
     * метод для получения всех токенов
     */
    List<PasswordRecoveryToken> getAllPasswordRecoveryTokens();

    /**
     * метод для добавления токена в базу
     *
     * @param passwordRecoveryToken - токен
     */
    void addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);


    /**
     * метод для удаления токена из базы
     *
     * @param passwordRecoveryToken - токен
     */
    void deletePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);

    /**
     * метод для обновления токена в базе
     *
     * @param passwordRecoveryToken - токен
     */
    void updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken);


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
}
