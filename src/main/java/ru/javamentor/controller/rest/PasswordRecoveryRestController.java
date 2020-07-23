package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.User;
import ru.javamentor.service.passwordRecoveryToken.PasswordRecoveryTokenService;
import ru.javamentor.service.user.UserService;

@RestController
@RequestMapping("/api")
public class PasswordRecoveryRestController {

    private PasswordRecoveryTokenService passwordRecoveryTokenService;
    private UserService userService;

    @Autowired
    public PasswordRecoveryRestController(PasswordRecoveryTokenService passwordRecoveryTokenService, UserService userService) {
        this.passwordRecoveryTokenService = passwordRecoveryTokenService;
        this.userService = userService;
    }

    @PostMapping("/recoveryPassword/getCode")
    public ResponseEntity<PasswordRecoveryToken> sendRecoveryToken(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PasswordRecoveryToken passwordRecoveryToken = new PasswordRecoveryToken(user);
        passwordRecoveryTokenService.addPasswordRecoveryToken(passwordRecoveryToken);
        return new ResponseEntity<>(passwordRecoveryToken, HttpStatus.OK);
    }

}
