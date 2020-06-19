package ru.javamentor.controller.rest;

import com.github.scribejava.apis.facebook.FacebookAccessTokenJsonExtractor;
import com.github.scribejava.apis.vk.VKOAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.config.Facebook;
import ru.javamentor.config.VKontakte;
import ru.javamentor.model.User;
import ru.javamentor.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/authorization")
public class RegistrationThroughSocialNetworks {

    private final VKontakte vKontakte;
    private final Facebook facebook;

    public UserService userService;

    @Autowired
    public RegistrationThroughSocialNetworks(VKontakte vKontakte, Facebook facebook, UserService userService) {
        this.vKontakte = vKontakte;
        this.facebook = facebook;
        this.userService = userService;
    }

    @GetMapping("/returnCodeVK")
    public ResponseEntity<Object> getCodeThird(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        OAuth2AccessToken token = vKontakte.toGetTokenVK(code);
        String email = ((VKOAuth2AccessToken) token).getEmail();
        User currentUser = vKontakte.toCreateUser(token, email);
        if (userService.getUserByEmail(email) == null) {
            userService.addUserThroughSocialNetworks(currentUser);
        }
        userService.login(currentUser.getUsername(), currentUser.getPassword(), currentUser.getAuthorities());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/home"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/returnCodeFacebook")
    public ResponseEntity<Object> getCodeSecond(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        OAuth2AccessToken token = facebook.toGetTokenFacebook(code);
        User currentUser = facebook.toCreateUser(token);
        if (userService.getUserByEmail(currentUser.getUsername()) == null) {
            userService.addUserThroughSocialNetworks(currentUser);
        }
        userService.login(currentUser.getUsername(), currentUser.getPassword(), currentUser.getAuthorities());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/home"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}