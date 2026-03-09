package com.cintie.musicrecommender.recommendationservice.clients;

import com.cintie.musicrecommender.recommendationservice.dto.TrackVector;
import com.cintie.musicrecommender.recommendationservice.dto.UserVector;
import com.cintie.musicrecommender.recommendationservice.services.ServiceJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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

    public List<TrackVector> getRecommendations(String spotifyId){
        String serviceToken = serviceJwtService.generateServiceToken("recommendation-service");

        return webClient.get()
                .uri("http://spotify-service/internal/spotify/recommendations/{spotifyId}", spotifyId)
                .header("Authorization", "Bearer " + serviceToken)
                .retrieve()
                .bodyToFlux(TrackVector.class)
                .collectList()
                .block();
    }
}
