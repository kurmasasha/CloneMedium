package ru.javamentor.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javamentor.dao.passwordRecoveryToken.PasswordRecoveryTokenDao;
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.mailSender.MailSender;
import ru.javamentor.service.passwordRecoveryToken.PasswordRecoveryTokenService;
import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * Тесты для класса PasswordRecoveryTokenServiceImpl
 *
 * @version 1.0
 * @author Java Mentor
 */

@RunWith(SpringRunner.class)
@SpringBootTest
class PasswordRecoveryTokenServiceImplTest {

    @Autowired
    PasswordRecoveryTokenService passwordRecoveryTokenService;

    @MockBean
    PasswordRecoveryTokenDao passwordRecoveryTokenDao;

    @MockBean
    MailSender mailSender;

    /**
     * Проверка добавления токена
     */
    @Test
    void addPasswordRecoveryToken() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        PasswordRecoveryToken token = new PasswordRecoveryToken(user);
        Assert.assertTrue("Проверка возвращаемого значения",
                passwordRecoveryTokenService.addPasswordRecoveryToken(token));
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).addPasswordRecoveryToken(token);
    }

    /**
     * Проверка обработки ошибок при добавлении токена
     */
    @Test
    void failAddPasswordRecoveryToken() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        PasswordRecoveryToken token = new PasswordRecoveryToken(user);
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .addPasswordRecoveryToken(token);
        boolean[] result = new boolean[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            result[0] = passwordRecoveryTokenService.addPasswordRecoveryToken(token);
        });
        Assert.assertFalse("Проверка возвращаемого значения", result[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).addPasswordRecoveryToken(token);
    }

    /**
     * Проверка удаления токена из базы
     */
    @Test
    void deletePasswordRecoveryToken() {
        PasswordRecoveryToken token = new PasswordRecoveryToken(new User());
        token.setId(1L);
        Assert.assertTrue("Проверка возвращаемого значения",
                passwordRecoveryTokenService.deletePasswordRecoveryToken(token));
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).deletePasswordRecoveryToken(token);
    }

    /**
     * Проверка обработки ошибок при удалении токена из базы
     */
    @Test
    void failDeletePasswordRecoveryToken() {
        PasswordRecoveryToken token = new PasswordRecoveryToken(new User());
        token.setId(1L);
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .deletePasswordRecoveryToken(token);
        boolean[] result = new boolean[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            result[0] = passwordRecoveryTokenService.deletePasswordRecoveryToken(token);
        });
        Assert.assertFalse("Проверка возвращаемого значения", result[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).deletePasswordRecoveryToken(token);
    }

    /**
     * Проверка получения всех токенов
     */
    @Test
    void getAllPasswordRecoveryTokens() {
        Mockito.doReturn(new ArrayList<PasswordRecoveryToken>())
                .when(passwordRecoveryTokenDao)
                .getAllPasswordRecoveryTokens();
        Assert.assertNotNull("Проверка возвращаемого объекта",
                passwordRecoveryTokenService.getAllPasswordRecoveryTokens());
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getAllPasswordRecoveryTokens();
    }

    /**
     * Проверка обработки ошибок при получении всех токенов
     */
    @Test
    void failGetAllPasswordRecoveryTokens() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .getAllPasswordRecoveryTokens();
        List<?>[] lists = new List[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            lists[0] = passwordRecoveryTokenService.getAllPasswordRecoveryTokens();
        });
        Assert.assertNull("Проверка возвращаемого объекта", lists[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getAllPasswordRecoveryTokens();
    }

    /**
     * Проверка для обновления токена в базе
     */
    @Test
    void updatePasswordRecoveryToken() {
        PasswordRecoveryToken token = new PasswordRecoveryToken();
        boolean result = passwordRecoveryTokenService.updatePasswordRecoveryToken(token);
        Assert.assertTrue("Проверка возвращаемого значения", result);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).updatePasswordRecoveryToken(token);
    }

    /**
     * Проверка обработки ошибок при  обновлении токена в базе
     */
    @Test
    void failUpdatePasswordRecoveryToken() {
        PasswordRecoveryToken token = new PasswordRecoveryToken();
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .updatePasswordRecoveryToken(token);
        boolean[] result = new boolean[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            result[0] = passwordRecoveryTokenService.updatePasswordRecoveryToken(token);
        });
        Assert.assertFalse("Проверка возвращаемого значения", result[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).updatePasswordRecoveryToken(token);
    }

    /**
     * Проверка получения токена по id
     */
    @Test
    void getPasswordRecoveryTokenById() {
        Mockito.doReturn(new PasswordRecoveryToken())
                .when(passwordRecoveryTokenDao)
                .getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
        PasswordRecoveryToken token = passwordRecoveryTokenService.getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
        Assert.assertNotNull("Проверка возвращаемого объекта", token);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка обработки ошибок при получения токена по id
     */
    @Test
    void failGetPasswordRecoveryTokenById() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
        PasswordRecoveryToken[] tokens = new PasswordRecoveryToken[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            tokens[0] = passwordRecoveryTokenService.getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
        });
        Assert.assertNull("Проверка возвращаемого объекта", tokens[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getPasswordRecoveryTokenById(ArgumentMatchers.anyLong());
    }

    /**
     * Проверка получения токена по хэшу
     */
    @Test
    void getPasswordRecoveryTokenByToken() {
        PasswordRecoveryToken tokenFromDao = new PasswordRecoveryToken();
        tokenFromDao.setHashEmail("");
        Mockito.doReturn(tokenFromDao)
                .when(passwordRecoveryTokenDao)
                .getPasswordRecoveryTokenByToken("");
        PasswordRecoveryToken token = passwordRecoveryTokenService.getPasswordRecoveryTokenByToken("");
        Assert.assertNotNull("Проверка возвращаемого объекта", token);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getPasswordRecoveryTokenByToken("");
    }

    /**
     * Проверка обработки ошибок при получения токена по хэшу
     */
    @Test
    void failGetPasswordRecoveryTokenByToken() {
        Mockito.doThrow(new TransactionRequiredException())
                .when(passwordRecoveryTokenDao)
                .getPasswordRecoveryTokenByToken(anyString());
        PasswordRecoveryToken[] tokens = new PasswordRecoveryToken[1];
        Assertions.assertThrows(RuntimeException.class, () -> {
            tokens[0] = passwordRecoveryTokenService.getPasswordRecoveryTokenByToken(anyString());
        });
        Assert.assertNull("Проверка возвращаемого объекта", tokens[0]);
        Mockito.verify(passwordRecoveryTokenDao,
                Mockito.times(1)).getPasswordRecoveryTokenByToken(anyString());
    }

    /**
     * Проверка отправления токена
     */
    @Test
    void sendPasswordRecoveryToken() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        user.setId(1L);
        PasswordRecoveryToken token = new PasswordRecoveryToken(user);
        token.setId(1L);
        token.setHashEmail("");
        Mockito.doNothing()
                .when(mailSender)
                .send(anyString(), anyString(), anyString());
        passwordRecoveryTokenService.sendPasswordRecoveryToken(token);
        Mockito.verify(mailSender, Mockito.times(1)).send(anyString(), anyString(), anyString());
    }

    /**
     * Проверка обработки ошибок при отправлении токена
     */
    @Test
    void failSendPasswordRecoveryToken() {
        User user = new User("firstName", "lastName", "username",
                "password", new Role("ADMIN"));
        PasswordRecoveryToken token = new PasswordRecoveryToken(user);
        token.setHashEmail("");
        Mockito.doThrow(new TransactionRequiredException())
                .when(mailSender)
                .send(anyString(), anyString(), anyString());
        Assertions.assertThrows(RuntimeException.class, () -> {
            passwordRecoveryTokenService.sendPasswordRecoveryToken(token);
        });
        Mockito.verify(mailSender, Mockito.times(1)).send(anyString(), anyString(), anyString());
    }

}
