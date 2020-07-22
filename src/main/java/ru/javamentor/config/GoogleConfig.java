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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;
import ru.javamentor.service.RoleService;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;


@Component
@Getter
//@PropertySource("classpath:social.properties")
public class GoogleConfig {

    private final RoleService roleService;

    private final OAuth20Service service;

    private final String authorizationUrl;

    @Autowired
    public GoogleConfig(RoleService roleService) {
        this.roleService = roleService;
        this.service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid profile email") // replace with desired scope
                .callback(callbackUrl)
                .build(GoogleApi20.instance());
        this.authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();
    }

    @Value("${google.protected.resource.url}")
    private String PROTECTED_RESOURCE_URL;

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    String clientSecret;

    @Value("${google.callbackUrl}")
    String callbackUrl;


    final String secretState = "secret" + new Random().nextInt(999_999);




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
