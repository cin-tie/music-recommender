package com.cintie.musicrecommender.authservice.security;

import com.cintie.musicrecommender.authservice.filters.InternalServiceJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2SuccessHandler successHandler;
    private final InternalServiceJwtFilter internalServiceJwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers("/internal/**").hasRole("SERVICE")
                        .anyRequest().denyAll()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler)
                        .loginPage("/oauth2/authorization/spotify")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                ).addFilterBefore(
                        internalServiceJwtFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}
