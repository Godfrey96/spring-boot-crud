package com.skomane.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .authorizeHttpRequests(auth -> {
                   auth.requestMatchers(
                           "/api/v1/**",
                           "/v2/api-docs",
                           "/v3/api-docs",
                           "/v3/api-docs/**",
                           "/swagger-resources",
                           "/swagger-resources/**",
                           "/configurations/ui",
                           "/configurations/security",
                           "/swagger-ui/**",
                           "/webjars/**",
                           "/swagger-ui.html"
                   ).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
                    auth.anyRequest().authenticated();
                });
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }
}
