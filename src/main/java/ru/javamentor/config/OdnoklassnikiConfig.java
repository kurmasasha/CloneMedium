package ru.javamentor.config;

import com.github.scribejava.apis.OdnoklassnikiApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.role.RoleService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Класс отвечающий за Odnoklassniki- авторизацию
 *
 * @version 1.0
 * @author Java Mentor
 */

@Configuration
public class OdnoklassnikiConfig implements SocialConfig {
    private final RoleService roleService;

    @Autowired
    public OdnoklassnikiConfig(RoleService roleService) {
        this.roleService = roleService;
    }

    public static final String PROTECTED_RESOURCE_URL = "https://api.ok.ru/api/users/getCurrentUser";

    @Value("${ok.clientId}")
    private String clientId;

    @Value("${ok.clientSecret}")
    private String clientSecret;

    @Value("${ok.customScope}")
    private String customScope;

    @Value("${ok.callbackUrl}")
    private String callbackUrl;

    @Value("${ok.publicKey}")
    private String publicKey;


    private OAuth20Service service;


    /**
     Метод для сборки URL из clientId, clientSecret и callbackUrl приложения разработчика, которые отправляются в RestController
     Поле OAuth20Service инициализируется один раз при первой авторизации
     */
    public String getAuthorizationUrl() {
        if (this.service == null) {
            this.service = new ServiceBuilder(clientId)
                    .apiSecret(clientSecret)
                    .defaultScope(customScope) // replace with desired scope
                    .callback(callbackUrl)
                    .build(OdnoklassnikiApi.instance());
        }
        return this.service.createAuthorizationUrlBuilder()
                .scope(customScope)
                .build();
    }

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
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL + "?application_key=" + publicKey);
        service.signRequest(token, request);
        try (Response response = service.execute(request)) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            String password = jsonObj.optString("uid");
            String firstName = jsonObj.optString("first_name");
            String lastName = jsonObj.optString("last_name");
            String email = jsonObj.getString("email");
            Role roleUser = roleService.getRoleByName("USER");
            User user = new User(firstName, lastName, email, password, roleUser);
            user.setSocialNetwork("Odnoklassniki");
            return user;
        } catch (JSONException e) {
            return null;
        }
    }

}
