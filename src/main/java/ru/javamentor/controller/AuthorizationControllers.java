package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javamentor.config.FacebookConfig;
import ru.javamentor.config.GoogleConfig;
import ru.javamentor.config.VKontakteConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Контроллер для перенаправления на соцсети при авторизации
 *
 * @author Java Mentor
 * @version 1.0
 */
@Controller
public class AuthorizationControllers {

    private final VKontakteConfig vKontakteConfig;
    private final FacebookConfig facebookConfig;
    private final GoogleConfig googleConfig;

    private URI authUrl;

    @Autowired
    public AuthorizationControllers(VKontakteConfig vKontakteConfig, FacebookConfig facebookConfig, GoogleConfig googleConfig) {
        this.vKontakteConfig = vKontakteConfig;
        this.facebookConfig = facebookConfig;
        this.googleConfig = googleConfig;
    }

    /**
     * метод для перенаправления на страницу авторизации Вконтакте
     *
     * @return ResponseEntity, который содержит заголовок с URI
     */
    @GetMapping("/authorization/vkAuthorization")
    public ResponseEntity<Object> redirectToVK() throws URISyntaxException {
        this.authUrl = new URI(vKontakteConfig.getAuthorizationUrl());
        return setLocation();
    }

    /**
     * метод для перенаправления на страницу авторизации Facebook
     *
     * @return ResponseEntity, который содержит заголовок с URI
     */
    @GetMapping("/authorization/facebookAuthorization")
    public ResponseEntity<Object> redirectFacebook() throws URISyntaxException {
        this.authUrl = new URI(facebookConfig.getAuthorizationUrl());
        return setLocation();
    }
    /**
     * метод для перенаправления на страницу авторизации Google
     *
     * @return ResponseEntity, который содержит заголовок с URI
     */
    @GetMapping("/authorization/googleAuthorization")
    public ResponseEntity<Object> redirectToGoogle() throws URISyntaxException {
        this.authUrl = new URI(googleConfig.getAuthorizationUrl());
        return setLocation();
    }
    /**
     * метод для установления перенапрвления
     *
     * @return ResponseEntity, который содержит заголовок с URI
     */
    private ResponseEntity<Object> setLocation() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(authUrl);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}