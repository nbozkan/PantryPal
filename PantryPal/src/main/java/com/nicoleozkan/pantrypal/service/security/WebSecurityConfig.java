package com.nicoleozkan.pantrypal.service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/index", "/signup", "/login").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .defaultSuccessUrl("/profile", true)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/index");

        return http.build();
    }
}