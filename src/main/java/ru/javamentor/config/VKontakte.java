package ru.javamentor.config;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Класс отвечающий за VK- авторизацию
 *
 * @version 1.0
 * @autor Java Mentor
 */
@Component
public class VKontakte {

    RoleService roleService;

    public VKontakte() {
    }

    @Autowired
    public VKontakte(RoleService roleService) {
        this.roleService = roleService;
    }

    public static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v=" + VkontakteApi.VERSION;

    final String clientId = "7499839";
    final String clientSecret = "yTAv4wjNeGubFJExIO72";
    final String customScope = "email";

    @Getter
    final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope(customScope) // replace with desired scope
            .callback("http://localhost:5050/authorization/returnCodeVK")
            .build(VkontakteApi.instance());

    @Getter
    final String authorizationUrl = service.createAuthorizationUrlBuilder()
            .scope(customScope)
            .build();

    /**
     * Метод для получения OAuth2AccessToken от VK
     * @param code -параметр , с которым возвращается пользователь с FB
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken toGetTokenVK(String code) throws InterruptedException, ExecutionException, IOException {
        return service.getAccessToken(AccessTokenRequestParams.create(code).scope(customScope));
    }
    /**
     * Метод для создания нового пользователя с помошью OAuth2AccessToken
     * @param token - токен
     * @return User - пользователь в системе
     */
    public User toCreateUser(OAuth2AccessToken token, String email) throws InterruptedException, ExecutionException, IOException {
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(token, request);
        try (Response response = service.execute(request)) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            JSONArray jArray = jsonObj.getJSONArray("response");
            String password = jArray.getJSONObject(0).optString("id");
            String firstName = jArray.getJSONObject(0).optString("first_name");
            String lastName = jArray.getJSONObject(0).optString("last_name");
            Role roleUser = roleService.getRoleByName("USER");
            return new User(firstName, lastName, email, password, roleUser);
        }
    }
}