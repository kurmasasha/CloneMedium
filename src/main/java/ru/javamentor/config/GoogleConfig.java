package ru.javamentor.config;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.role.RoleService;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Класс отвечающий за Google- авторизацию
 *
 * @version 1.0
 * @author Java Mentor
 */

@Configuration
public class GoogleConfig implements SocialConfig {
    private final RoleService roleService;

    @Autowired
    public GoogleConfig(RoleService roleService) {
        this.roleService = roleService;

    }

    private final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Value("${google.callbackUrl}")
    private String callbackUrl;

    @Value("${google.customScope}")
    private String customScope;


    private OAuth20Service service;

    /**
     Метод для сборки URL из clientId, clientSecret и callbackUrl приложения разработчика, которые отправляются в RestController
     Поле OAuth20Service инициализируется один раз при первой авторизации
     */
    public String getAuthorizationUrl() {
        if (this.service == null) {
            this.service = new ServiceBuilder(clientId)
                    .apiSecret(clientSecret)
                    .defaultScope("openid profile email") // replace with desired scope
                    .callback(callbackUrl)
                    .build(GoogleApi20.instance());
        }
        return service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();
    }

    final String secretState = "secret" + new Random().nextInt(999_999);

    /**
     * Метод для получения OAuth2AccessToken
     */
    public OAuth2AccessToken toGetToken(String code) throws InterruptedException, ExecutionException, IOException {
        return service.getAccessToken(AccessTokenRequestParams.create(code).scope(customScope));
    }

    /**
     * Метод для создания нового пользователя с помошью OAuth2AccessToken
     */
    public User toCreateUser(OAuth2AccessToken token) throws InterruptedException, ExecutionException, IOException {
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(token, request);
        try (Response response = service.execute(request)) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            String password = jsonObj.getString("sub");
            System.out.println(response);
            String firstName = jsonObj.getString("given_name");
            String lastName = jsonObj.getString("family_name");
            String email = jsonObj.getString("email");
            Role roleUser = roleService.getRoleByName("USER");
            User user = new User(firstName, lastName, email, password, roleUser);
            user.setSocialNetwork("Google");
            return user;
        }catch (JSONException e){
            return null;
        }
    }
}
