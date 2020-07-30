package ru.javamentor.service.passwordRecoveryToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.passwordRecoveryToken.PasswordRecoveryTokenDao;
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.User;
import ru.javamentor.service.mailSender.MailSender;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
     * метод для удаления токена из базы
     *
     * @param passwordRecoveryToken - токен
     */
    @Transactional
    @Override
    public boolean deletePasswordRecoveryToken(PasswordRecoveryToken passwordRecoveryToken) {
        try {
            passwordRecoveryTokenDao.deletePasswordRecoveryToken(passwordRecoveryToken);
            log.debug("IN deletePasswordRecoveryToken - token.id {} is deleted", passwordRecoveryToken.getId());
            return true;
        } catch (Exception e) {
            log.error("IN deletePasswordRecoveryToken - token.id {} is not deleted with exception {}", passwordRecoveryToken.getId(), e.getMessage());
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
     * @param token - хэш, по которому ищем
     */
    @Transactional
    @Override
    public PasswordRecoveryToken getPasswordRecoveryTokenByToken(String token) {
        try {
            PasswordRecoveryToken passwordRecoveryToken = passwordRecoveryTokenDao.getPasswordRecoveryTokenByToken(token);
            if(passwordRecoveryToken != null) {
                log.debug("IN getPasswordRecoveryTokenByHash - passwordRecoveryToken.hashMail: {} was successfully found", passwordRecoveryToken.getHashEmail());
            } else {
                log.debug("IN getPasswordRecoveryTokenByHash - passwordRecoveryToken was not found");
            }
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

    @Override
    public boolean isValid(PasswordRecoveryToken passwordRecoveryToken) {
        return Duration.between(passwordRecoveryToken.getStartTime(), LocalDateTime.now()).toMinutes() < 20;
    }

    /**
     * метод для генерации временого пароля
     *
     * @return
     */
    @Override
    public String generateTempPass() {
        final String char_list = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();

        StringBuilder randStr = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(char_list.length());
            char ch = char_list.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * отправление временного пароля пользователю
     *
     * @param user     - кому отправляем
     * @param tempPass - временный пароль
     */
    @Override
    public void sendTempPass(User user, String tempPass) {
        try {
            String message = String.format(
                    "Вами была отправлена заявка на сброс пароля. Ваш новый пароль для входа на сайт: " +
                            "%s", tempPass
            );
            mailSender.send(user.getUsername(), "Восстановление пароля", message);
            log.debug("IN sendTempPass - temporal password was successfully sent to user.id {}", user.getId());
        } catch (Exception e) {
            log.error("IN sendTempPass - temporal password was not sent with exception {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
