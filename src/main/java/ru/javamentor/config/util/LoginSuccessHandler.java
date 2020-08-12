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
        User user = (User) authentication.getPrincipal();

        boolean adminflag = false;
        boolean userflag = false;

        for (GrantedAuthority i : user.getAuthorities()) {
            if (!adminflag) {
                adminflag = (i.getAuthority().equals("ADMIN"));
            }
            if (!userflag) {
                userflag = (i.getAuthority().equals("USER"));
            }
        }

        if (adminflag) {
            httpServletResponse.sendRedirect("/home");
        } else if (userflag) {
            httpServletResponse.sendRedirect("/home");
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
