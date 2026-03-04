package com.cintie.musicrecommender.recommendationservice.clients;

import com.cintie.musicrecommender.recommendationservice.dto.UserVector;
import com.cintie.musicrecommender.recommendationservice.services.ServiceJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class SpotifyServiceClient {

    private final WebClient webClient;
    private final ServiceJwtService serviceJwtService;

    public UserVector getUserVector(String spotifyId){
        String serviceToken = serviceJwtService.generateServiceToken("recommendation-service");

        return webClient.get()
            .uri("http://spotify-service/internal/spotify/user-vector/{spotifyId}", spotifyId)
            .header("Authorization", "Bearer " + serviceToken)
            .retrieve()
            .bodyToMono(UserVector.class)
            .block();
    }
}
