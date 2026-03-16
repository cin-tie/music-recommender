package com.cintie.musicrecommender.spotifyservice.clients;

import com.cintie.musicrecommender.spotifyservice.dto.AccessTokenResponse;
import com.cintie.musicrecommender.spotifyservice.services.ServiceJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;
    private final ServiceJwtService serviceJwtService;

    public AccessTokenResponse getSpotifyAccessToken(String spotifyId){
        String serviceToken = serviceJwtService.generateServiceToken("auth-service");

        return webClientBuilder.build().get()
                .uri("http://auth-service/internal/auth/spotify-token/{id}", spotifyId)
                .header("Authorization", "Bearer " + serviceToken)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .block();

    }
}
