package com.cintie.musicrecommender.spotifyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class SpotifyApiClient {

    private final WebClient webClient;

    public String getRecentlyPlayed(String accessToken){
        return webClient.get()
                .uri("https://api.spotify.com/v1/me/player/recently-played?limit=10")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
