package ru.javamentor.config.handler;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.javamentor.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
/**
 * Класс отвечающий за логику после аутентификации пользователя в системе
 * @autor Java Mentor
 * @version 1.0
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Метод вызывается после успешной аутентификации
     * @param httpServletRequest - объект запроса
     * @param httpServletResponse - объект ответа
     * @param authentication - представляет пользователя с точки зрения Spring Security
     * @return void
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities()
                .stream()
//                .anyMatch(role -> "ADMIN".equals(role.getAuthority()) || "ROLE_ADMIN".equals(role.getAuthority()))
                .anyMatch(role -> "ADMIN".equals(role.getAuthority()))
        ) {
            httpServletResponse.sendRedirect("/");
        } else if (user.getAuthorities()
                .stream()
                .anyMatch(role -> "USER".equals(role.getAuthority()) || "ROLE_USER".equals(role.getAuthority()))
        ) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", user);
            httpServletResponse.sendRedirect("/");
        }
    }
}
