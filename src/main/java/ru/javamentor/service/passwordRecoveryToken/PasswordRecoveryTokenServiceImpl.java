package ru.javamentor.service.passwordRecoveryToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.passwordRecoveryToken.PasswordRecoveryTokenDao;
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.service.mailSender.MailSender;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PasswordRecoveryTokenServiceImpl implements PasswordRecoveryTokenService {

    @Value("${site.link}")
    private String rootLink;

    private final PasswordRecoveryTokenDao passwordRecoveryTokenDao;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRecoveryTokenServiceImpl(PasswordRecoveryTokenDao passwordRecoveryTokenDao, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.passwordRecoveryTokenDao = passwordRecoveryTokenDao;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * метод для всего процесса добавления и отправки токена
     *
     * @param passwordRecoveryToken - токен
     */
    @Transactional
    @Override
    public boolean addPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        try {
            passwordRecoveryToken.setHashEmail(passwordEncoder.encode(passwordRecoveryToken.getUser().getUsername()));
            passwordRecoveryToken.setStartTime(LocalDateTime.now());
            passwordRecoveryTokenDao.addPasswordRecoveryToken(passwordRecoveryToken);
            sendPasswordRecoveryToken(passwordRecoveryToken);
            log.debug("IN addToken - token added with hash {} by user.id {}", passwordRecoveryToken.getHashEmail(), passwordRecoveryToken.getUser().getId());
            return true;
        } catch (Exception e) {
            log.error("IN addToken - token not added with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * метод для получения всех токенов
     */
    @Transactional
    @Override
    public List<PasswordRecoveryToken> getAllPasswordRecoveryTokens() {
        try {
            List<PasswordRecoveryToken> passwordRecoveryTokens = passwordRecoveryTokenDao.getAllPasswordRecoveryTokens();
            log.debug("IN getAllPasswordRecoveryTokens - all tokens have been successfully received");
            return passwordRecoveryTokens;
        } catch (Exception e) {
            log.error("IN getAllPasswordRecoveryTokens - have not been received with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }


    /**
     * метод для обновления токена в базе
     *
     * @param passwordRecoveryToken - токен
     */
    @Transactional
    @Override
    public boolean updatePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        try {
            passwordRecoveryTokenDao.updatePasswordRecoveryToken(passwordRecoveryToken);
            log.debug("IN updateToken - passwordRecoveryToken.id: {} successfully updated", passwordRecoveryToken.getId());
            return true;
        } catch (Exception e) {
            log.error("IN updateToken - passwordRecoveryToken not updated with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * получение токена по id
     *
     * @param id - id токена
     */
    @Transactional
    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenById(Long id) {
        try {
            PasswordRecoveryToken passwordRecoveryToken = passwordRecoveryTokenDao.getPasswordRecoveryTokenById(id);
            log.debug("IN getPasswordRecoveryTokenById - passwordRecoveryToken.id: {} was successfully found", passwordRecoveryToken.getId());
            return passwordRecoveryToken;
        } catch (Exception e) {
            log.error("IN getPasswordRecoveryTokenById - passwordRecoveryToken not selected with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }


    /**
     * получение токена по хэшу
     *
     * @param hashMail - хэш, по которому ищем
     */
    @Transactional
    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenByHash(String hashMail) {
        try {
            PasswordRecoveryToken passwordRecoveryToken = passwordRecoveryTokenDao.getPasswordRecoveryTokenByHash(hashMail);
            log.debug("IN getPasswordRecoveryTokenByHash - passwordRecoveryToken.hashMail: {} was successfully found", passwordRecoveryToken.getHashEmail());
            return passwordRecoveryToken;
        } catch (Exception e) {
            log.error("IN getPasswordRecoveryTokenById - passwordRecoveryToken was not selected with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Transactional
    @Override
    public void sendPasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        try {
            String message = String.format(
                    "Это письмо отправлено Вам, потому что вы запросили восстановление пароля. " +
                            "Если вы не запрашивали восстановление, проигнорируйте это письмо. " +
                            "Для восстановления пароля перейдите по ссылке: " +
                            "%s" + "recoveryPass/" + "%s", rootLink, passwordRecoveryToken.getHashEmail()
            );
            mailSender.send(passwordRecoveryToken.getUser().getUsername(), "Восстановление пароля", message);
            log.debug("IN sendPasswordRecoveryToken - passwordRecoveryToken.id: {} was successfully sent to user.id {}", passwordRecoveryToken.getId(), passwordRecoveryToken.getUser().getId());
        } catch (Exception e) {
            log.error("IN sendPasswordRecoveryToken - passwordRecoveryToken was not sent with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
