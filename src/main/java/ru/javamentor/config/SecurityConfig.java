package ru.javamentor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.javamentor.config.handler.LoginSuccessHandler;

@Configuration
@ComponentScan("ru.javamentor")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .successHandler(new LoginSuccessHandler())
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/registration/**", "/activate/*", "/api/free-user/**",
                                        "/js/util/topic-in-card.js",
                                        "/webjars/bootstrap/4.3.1/css/bootstrap.min.css",
                                        "/css/style.css", "/img/logo.svg",
                                        "/webjars/jquery/3.4.1/jquery.min.js",
                                        "/webjars/bootstrap/4.3.1/js/bootstrap.min.js",
                                        "/js/all_topics_events.js",
                                        "/js/getAllTopicsByHashtag.js",
                                        "/js/getAndPrintModeratedTopics.js").permitAll()

                .antMatchers("/authorization/**").permitAll()
                .antMatchers("/login", "/").anonymous()
                .antMatchers("/admin/**", "/api/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/user/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
