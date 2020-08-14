package ru.javamentor.controller.rest;

import com.github.scribejava.apis.vk.VKOAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.config.FacebookConfig;
import ru.javamentor.config.GoogleConfig;
import ru.javamentor.config.OdnoklassnikiConfig;
import ru.javamentor.config.VKontakteConfig;
import ru.javamentor.model.User;
import ru.javamentor.service.user.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

/**
 * Rest контроллер для авторизации с помощью соцсетей
 *
 * @author Java Mentor
 * @version 1.0
 */
@RestController
@RequestMapping("/authorization")
public class RegistrationThroughSocialNetworks {

    private final VKontakteConfig vKontakteConfig;
    private final FacebookConfig facebookConfig;
    private final GoogleConfig googleConfig;
    private final OdnoklassnikiConfig odnoklassnikiConfig;

    public UserService userService;

    private User currentUser;
    private OAuth2AccessToken token;

    @Autowired
    public RegistrationThroughSocialNetworks(VKontakteConfig vKontakteConfig, FacebookConfig facebookConfig, UserService userService,
                                             GoogleConfig googleConfig, OdnoklassnikiConfig odnoklassnikiConfig) {
        this.odnoklassnikiConfig = odnoklassnikiConfig;
        this.vKontakteConfig = vKontakteConfig;
        this.facebookConfig = facebookConfig;
        this.googleConfig = googleConfig;
        this.userService = userService;
    }

    /**
     * метод для ВК-авторизации
     *
     * @param code - параметр запроса
     */
    @GetMapping("/returnCodeVK")
    public ResponseEntity<Object> getCodeVk(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        this.token = vKontakteConfig.toGetToken(code);
        this.currentUser = vKontakteConfig.toCreateUser(token);
        return authorizationAfterInitialization();
    }


    /**
     * метод для FB-авторизации
     *
     * @param code - параметр запроса
     */
    @GetMapping("/returnCodeFacebook")
    public ResponseEntity<Object> getCodeFacebook(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        this.token = facebookConfig.toGetToken(code);
        this.currentUser = facebookConfig.toCreateUser(token);
        return authorizationAfterInitialization();
    }

    /**
     * метод для Google-авторизации
     *
     * @param code - параметр запроса
     */
    @GetMapping("/returnCodeGoogle")
    public ResponseEntity<Object> getCodeGoogle(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        this.token = googleConfig.toGetToken(code);
        this.currentUser = googleConfig.toCreateUser(token);
        return authorizationAfterInitialization();
    }

    /**
     * метод для Ok-авторизации
     *
     * @param code - параметр запроса
     */
    @GetMapping("/returnCodeOdnoklassniki")
    public ResponseEntity<Object> getCodeOk(@RequestParam String code) throws InterruptedException, ExecutionException, IOException, URISyntaxException {
        this.token = odnoklassnikiConfig.toGetToken(code);
        this.currentUser = odnoklassnikiConfig.toCreateUser(token);
        return authorizationAfterInitialization();
    }

    /**
     * Метод общей авторизации
     *
     * @return ResponseEntity, который перенаправляет на страницу Home
     */
    private ResponseEntity<Object> authorizationAfterInitialization() throws URISyntaxException {
        if(currentUser == null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI("/login?errorEmail"));
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }
        if (userService.getUserByEmail(currentUser.getUsername()) == null) {
            userService.addUserThroughSocialNetworks(currentUser);
        }
        userService.login(currentUser.getUsername(), currentUser.getPassword(), currentUser.getAuthorities());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/home"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

    }
}