package ru.javamentor.config;

import com.github.scribejava.apis.FacebookApi;
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

@Component
public class Facebook {

    RoleService roleService;

    public Facebook() {
    }

    @Autowired
    public Facebook(RoleService roleService) {
        this.roleService = roleService;
    }

    public final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me?fields=id,first_name,last_name,email";

   final String clientId = "307372773616495";
   final String clientSecret = "b8687788f300561bb70131728272e269";
   final String customScope = "email";

    @Getter
    final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .defaultScope(customScope) // replace with desired scope
            .callback("http://localhost:5050/authorization/returnCodeFacebook")
            .build(FacebookApi.instance());

    @Getter
    final String authorizationUrl = service.createAuthorizationUrlBuilder()
            .scope(customScope)
            .build();

    public OAuth2AccessToken toGetTokenFacebook(String code) throws InterruptedException, ExecutionException, IOException {
        return service.getAccessToken(AccessTokenRequestParams.create(code).scope(customScope));
    }

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