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
//Тестовый комментарий
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

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
            httpServletResponse.sendRedirect("/home");
        } else if (user.getAuthorities()
                .stream()
                .anyMatch(role -> "USER".equals(role.getAuthority()) || "ROLE_USER".equals(role.getAuthority()))
        ) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", user);
            httpServletResponse.sendRedirect("/home");
        }
    }
}
