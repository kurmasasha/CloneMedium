package ru.javamentor.config;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.role.RoleService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Класс отвечающий за Facebook- авторизацию
 *
 * @version 1.0
 * @author Java Mentor
 */

@Component
public class FacebookConfig {

    RoleService roleService;

    public FacebookConfig() {
    }

    @Autowired
    public FacebookConfig(RoleService roleService) {
        this.roleService = roleService;
    }

    private final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me?fields=id,first_name,last_name,email";

    @Value("${facebook.clientId}")
    private String clientId;

    @Value("${facebook.clientSecret}")
    private String clientSecret;

    @Value("${facebook.customScope}")
    private String customScope;

    @Value("${facebook.callbackUrl}")
    private String callbackUrl;

    @Getter
    private OAuth20Service service;

    public String getAuthorizationUrl(){
        if(this.service == null) {
            this.service = new ServiceBuilder(clientId)
                    .apiSecret(clientSecret)
                    .defaultScope(customScope) // replace with desired scope
                    .callback(callbackUrl)
                    .build(FacebookApi.instance());
        }
        return this.service.createAuthorizationUrlBuilder()
                .scope(customScope)
                .build();
    }

    /**
     * Метод для получения OAuth2AccessToken от FB
     *
     * @param code -параметр , с которым возвращается пользователь с FB
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken toGetTokenFacebook(String code) throws InterruptedException, ExecutionException, IOException {
        return service.getAccessToken(AccessTokenRequestParams.create(code).scope(customScope));
    }

    /**
     * Метод для создания нового пользователя с помошью OAuth2AccessToken
     *
     * @param token - токен
     * @return User - пользователь в системе
     */
    public User toCreateUser(OAuth2AccessToken token) throws InterruptedException, ExecutionException, IOException {
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(token, request);
        try (Response response = service.execute(request)) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            String password = jsonObj.getString("id");
            String email = jsonObj.getString("email");
            String firstName = jsonObj.getString("first_name");
            String lastName = jsonObj.getString("last_name");
            Role roleUser = roleService.getRoleByName("USER");
            return new User(firstName, lastName, email, password, roleUser);
        }
    }
}