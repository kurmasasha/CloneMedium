package ru.javamentor.config;

import com.github.scribejava.core.model.OAuth2AccessToken;
import ru.javamentor.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Интерфейс отвечающий за авторизацию пользователя
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface SocialConfig {

    /**
        Метод для сборки URL из clientId, clientSecret и callbackUrl приложения разработчика, которые отправляются в RestController
        Поле OAuth20Service инициализируется один раз при первой авторизации
     */
    String getAuthorizationUrl();

    /**
     * Метод для получения OAuth2AccessToken
     *
     * @param code -параметр , с которым возвращается пользователь
     * @return OAuth2AccessToken
     */
    OAuth2AccessToken toGetToken(String code) throws InterruptedException, ExecutionException, IOException;
    /**
     * Метод для создания нового пользователя с помошью OAuth2AccessToken
     *
     * @param token - токен
     * @return User - пользователь в системе
     */
    User toCreateUser(OAuth2AccessToken token) throws InterruptedException, ExecutionException, IOException;
}
