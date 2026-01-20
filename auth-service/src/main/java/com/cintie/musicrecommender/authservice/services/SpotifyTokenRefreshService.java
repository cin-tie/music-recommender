package com.cintie.musicrecommender.authservice.services;

import com.cintie.musicrecommender.authservice.dto.SpotifyTokenResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpotifyTokenRefreshService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.spotify.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.spotify.client-secret}")
    private String clientSecret;

    public User refresh(User user){

        SpotifyTokenResponse response = webClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .headers(h -> h.setBasicAuth(clientId, clientSecret))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(
                        "grant_type=refresh_token" +
                                "&refresh_token=" + user.getRefreshToken()
                )
                .retrieve()
                .bodyToMono(SpotifyTokenResponse.class)
                .block();

        if(response == null || response.accessToken() == null) {
            throw new IllegalStateException("Failed to refresh Spotify token");
        }

        user.setAccessToken(response.accessToken());
        user.setTokenExpiry(Instant.now().plusSeconds(response.expiresIn()));

        if(response.refreshToken() != null){
            user.setRefreshToken(response.refreshToken());
        }

        return userRepository.save(user);
    }
}
