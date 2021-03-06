package ru.javamentor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.javamentor.config.util.LoginSuccessHandler;

/**
 * Класс отвечающий за конфигурацию секьюрности
 *
 * @author Java Mentor
 * @version 1.0
 */
@Configuration
@ComponentScan("ru.javamentor")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * метод устанавливает userDetailsService и passwordEncoder
   *
   * @param auth - объект с помощью которого конфигурируем аутентификацию
   * @return void
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  /**
   * метод для установки секьюрности разным URL
   *
   * @param http - объект для конфигурации секьюрности
   * @return void
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()

        .loginPage("/login").failureUrl("/login?error=true")
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
        .antMatchers("/", "/registration/**", "/activate/*", "/api/free-user/**",
            "/js/**", "/webjars/**", "/css/**", "/img/**", "/api/topic/**", "/topic-img/**",
            "/user-img/**")
        .permitAll()
        .antMatchers("/authorization/**", "/api/user/topic/**", "/topic/**", "/recoveryPass/**")
        .permitAll()
        .antMatchers("/login").anonymous()
        .antMatchers("/admin/**", "/api/admin/**").hasAuthority("ADMIN")
        .antMatchers("/api/user/**", "/user/**").hasAnyAuthority("USER", "ADMIN")
        .anyRequest().authenticated();
  }

  /**
   * метод конфигурирующий бин для шифрования пароля
   *
   * @return BCryptPasswordEncoder
   */
  @Bean
  public PasswordEncoder getBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(getBCryptPasswordEncoder());
    return authProvider;
  }
}
