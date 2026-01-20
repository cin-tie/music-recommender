package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient webClient;
    private final ServiceJwtService serviceJwtService;

    public AccessTokenResponse getSpotifyAccessToken(String spotifyId){
        String serviceToken = serviceJwtService.generateServiceToken("auth-service");

        return webClient.get()
                .uri("http://auth-service/internal/auth/spotify-token/{id}", spotifyId)
                .header("Authorization", "Bearer " + serviceToken)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .block();

    }
}
