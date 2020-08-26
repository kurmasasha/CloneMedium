package ru.javamentor.config.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        boolean adminFlag = false;
        boolean userFlag = false;

        for (GrantedAuthority i : authentication.getAuthorities()) {
            if (!adminFlag) {
                adminFlag = (i.getAuthority().equals("ADMIN"));
            }
            if (!userFlag) {
                userFlag = (i.getAuthority().equals("USER"));
            }
        }

        if (adminFlag) {
            httpServletResponse.sendRedirect("/admin/allUsers");
        } else if (userFlag) {
            httpServletResponse.sendRedirect("/home");
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
