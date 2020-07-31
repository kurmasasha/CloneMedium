package ru.javamentor.dao.passwordRecoveryToken;

import org.springframework.stereotype.Repository;
import ru.javamentor.model.PasswordRecoveryToken;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PasswordRecoveryTokenDaoImpl implements PasswordRecoveryTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * метод для получения всех токенов
     */
    @Override
    public List<PasswordRecoveryToken> getAllPasswordRecoveryTokens() {
        return entityManager.createQuery("SELECT t FROM PasswordRecoveryToken t").getResultList();
    }

    /**
     * метод для добавления токена в базу
     *
     * @param passwordRecoveryToken - токен
     */
    @Override
    public void addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        entityManager.persist(passwordRecoveryToken);
    }

    /**
     * метод для удаления токена из базы
     *
     * @param passwordRecoveryToken - токен
     */
    @Override
    public void deletePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        entityManager.remove(passwordRecoveryToken);
    }

    /**
     * метод для обновления токена в базе
     *
     * @param passwordRecoveryToken - токен
     */
    @Override
    public void updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        entityManager.merge(passwordRecoveryToken);
    }

    /**
     * получение токена по id
     *
     * @param id - id токена
     */
    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenById(Long id) {
        return entityManager.find(PasswordRecoveryToken.class, id);
    }

    /**
     * получение токена по токену
     *
     * @param token - токен, по которому ищем
     */
    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenByToken(String token) {
        return entityManager.createQuery("SELECT p FROM PasswordRecoveryToken p WHERE p.hashEmail = :token", PasswordRecoveryToken.class)
                .setParameter("token", token).getResultStream().findFirst().orElse(null);
    }
}
