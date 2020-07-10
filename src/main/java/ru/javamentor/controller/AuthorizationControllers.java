package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javamentor.config.Facebook;
import ru.javamentor.config.Google;
import ru.javamentor.config.VKontakte;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Контроллер для перенаправления на соцсети при авторизации
 *
 * @version 1.0
 * @author Java Mentor
 */
@Controller
public class AuthorizationControllers {

    private final VKontakte vKontakte;
    private final Facebook facebook;
    private final Google google;

    @Autowired
    public AuthorizationControllers(VKontakte vKontakte, Facebook facebook, Google google) {
        this.vKontakte = vKontakte;
        this.facebook = facebook;
        this.google = google;
    }

    /**
     * метод для перенаправления на страницу авторизации Вконтакте
     * @return ResponseEntity, который содержит заголовок с URI
     */
    @GetMapping("/authorization/vkAuthorization")
    public ResponseEntity<Object> redirectToVK() throws URISyntaxException {
        URI vk = new URI(vKontakte.getAuthorizationUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(vk);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
    /**
     * метод для перенаправления на страницу авторизации Facebook
     * @return ResponseEntity, который содержит заголовок с URI
     */
    @GetMapping("/authorization/facebookAuthorization")
    public ResponseEntity<Object> redirectFacebook() throws URISyntaxException {
        URI vk = new URI(facebook.getAuthorizationUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(vk);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/authorization/googleAuthorization")
    public ResponseEntity<Object> redirectToGoogle() throws URISyntaxException {
        URI vk = new URI(google.getAuthorizationUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(vk);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}