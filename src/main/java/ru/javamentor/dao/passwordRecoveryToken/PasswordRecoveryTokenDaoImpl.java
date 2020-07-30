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

    @Override
    public List<PasswordRecoveryToken> getAllPasswordRecoveryTokens() {
        return entityManager.createQuery("SELECT t FROM PasswordRecoveryToken t").getResultList();
    }

    @Override
    public void addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        entityManager.persist(passwordRecoveryToken);
    }

    @Override
    public void updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        entityManager.merge(passwordRecoveryToken);
    }

    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenById(Long id) {
        return entityManager.find(PasswordRecoveryToken.class, id);
    }

    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenByHash(String hashMail) {
        return entityManager.createQuery("SELECT p FROM PasswordRecoveryToken p WHERE p.hashEmail = :hashMail", PasswordRecoveryToken.class)
                .setParameter("hashMail", hashMail).getResultStream().findFirst().orElse(null);
    }
}
