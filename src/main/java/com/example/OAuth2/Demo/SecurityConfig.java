package com.example.OAuth2.Demo;

import com.example.OAuth2.Demo.OAuth.CustomOAuth2User;
import com.example.OAuth2.Demo.OAuth.UserService;
import com.example.OAuth2.Demo.appUser.AppUserService;
import com.example.OAuth2.Demo.OAuth.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(AppUserService appUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserService = appUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/**")
//                .antMatchers(
//                        "/oauth/**",
//                        "/login",
//                        "/error",
//                        "/webjars/**",
//                        "/static/**",
//                        "manifest.json")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .cors()
                .and()
//                .csrf().disable()
                .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("onAuthenticationSuccess");
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                        System.out.println("oauthUser: " + oauthUser.getEmail());
                        System.out.println("oauthUserName: " + oauthUser.getName());
                        System.out.println("oauthUserAttributes: " + oauthUser.getAttributes());
                        System.out.println("/end");
                        userService.processOAuthPostLogin(oauthUser.getAttribute("login"));
                        httpServletResponse.sendRedirect("/");
                    }
                });
    }

    @Autowired
    private UserService userService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
