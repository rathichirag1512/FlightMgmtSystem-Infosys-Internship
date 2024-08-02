package com.agile.flightMgmtSystem.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.agile.flightMgmtSystem.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService service;

    @Autowired
    private EncoderConfig config;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(config.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/newUser").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email") // Specify that the email parameter should be used as the username
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        String userType = service.getType(authentication.getName()); // Assuming getType method fetches user type
                        if ("admin".equalsIgnoreCase(userType)) {
                            response.sendRedirect("/aindex");
                        } else if ("customer".equalsIgnoreCase(userType)) {
                            response.sendRedirect("/cindex");
                        } else {
                            response.sendRedirect("/loginpage?error=Unknown user type.");
                        }
                    }
                })
        .and()
        .logout()
        .logoutUrl("/logout") // Spring Security's logout URL
        .logoutSuccessUrl("/login?logout") // Redirect to login page with logout message
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .permitAll()
        .and()
        .csrf().disable();

    }

}