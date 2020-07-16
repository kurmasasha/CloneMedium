package ru.javamentor.config;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Component
public class Google {

    RoleService roleService;

    public Google() {}

    @Autowired
    public Google(RoleService roleService) {
        this.roleService = roleService;
    }

    public final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    final String clientId = "483915897030-pt9gdsr0s1phvjadc7kdi1t82j1jfnvs.apps.googleusercontent.com";
    final String clientSecret = "2lZgztTFGbD9JGOJxB8QEPvd";
    final String callbackUrl = "http://localhost:5050/authorization/returnCodeGoogle";
    final String secretState = "secret" + new Random().nextInt(999_999);

    @Getter
    final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope("openid profile email") // replace with desired scope
            .callback(callbackUrl)
            .build(GoogleApi20.instance());

    @Getter
    final String authorizationUrl = service.createAuthorizationUrlBuilder()
            .state(secretState)
            .build();

    public OAuth2AccessToken toGetTokenGoogle(String code) throws InterruptedException, ExecutionException, IOException {
        System.out.println(code);
        return service.getAccessToken(code);
    }

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
            return new User(firstName, lastName, email, password, roleUser);
        }
    }
}
