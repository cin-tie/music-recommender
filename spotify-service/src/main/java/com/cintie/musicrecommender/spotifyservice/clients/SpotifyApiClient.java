package com.cintie.musicrecommender.spotifyservice.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class SpotifyApiClient {

    private final WebClient webClient;

    private final int limit = 50; // TODO: Get all where needed

    public String getRecentlyPlayed(String accessToken){
        return webClient.get()
                .uri("https://api.spotify.com/v1/me/player/recently-played?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTopTracks(String accessToken){
        return webClient.get()
                .uri("https://api.spotify.com/v1/me/top/tracks?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTopArtists(String accessToken){
        return webClient.get()
                .uri("https://api.spotify.com/v1/me/top/artists?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getSavedTracks(String accessToken){
        return webClient.get()
                .uri("hhttps://api.spotify.com/v1/me/tracks?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
