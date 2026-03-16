package com.cintie.musicrecommender.spotifyservice.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyApiClient {

    private final WebClient spotifyWebClient;

    private final int limit = 50; // TODO: Get all where needed

    public String getRecentlyPlayed(String accessToken){
        return spotifyWebClient.get()
                .uri("https://api.spotify.com/v1/me/player/recently-played?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTopTracks(String accessToken){
        return spotifyWebClient.get()
                .uri("https://api.spotify.com/v1/me/top/tracks?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getTopArtists(String accessToken){
        return spotifyWebClient.get()
                .uri("https://api.spotify.com/v1/me/top/artists?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getSavedTracks(String accessToken){
        return spotifyWebClient.get()
                .uri("https://api.spotify.com/v1/me/tracks?limit={limit}", limit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getAudioFeatures(String accessToken, List<String> trackIds) {
        int size = trackIds.size();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < size; i += 50) {
            String ids = String.join(",", trackIds.subList(i, Math.min(size, i + 50)));

            result.append(
                    spotifyWebClient.get()
                            .uri("https://api.spotify.com/v1/audio-features?ids={ids}", ids)
                            .header("Authorization", "Bearer " + accessToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block()
            );
        }

        return result.toString();
    }

    public String getTracks(String accessToken, List<String> trackIds){
        int size = trackIds.size();
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < size; i += 50){
            String ids = String.join(",", trackIds.subList(i, Math.min(size, i + 50)));

            result.append(spotifyWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("https://api.spotify.com/v1/tracks")
                            .queryParam("ids", ids)
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        }

        return result.toString();
    }

    public String getRecommendations(String accessToken, List<String> seedTracks, List<String> listArtists){
        String trackSeeds = String.join(",", seedTracks.subList(0, Math.min(5, seedTracks.size())));
        String artistSeeds = String.join(",", listArtists.subList(0, Math.min(5 - Math.min(5, seedTracks.size()), listArtists.size())));

        return spotifyWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("https://api.spotify.com/v1/recommendations")
                        .queryParam("limit", limit)
                        .queryParam("seed_tracks", trackSeeds)
                        .queryParam("seed_artists", artistSeeds)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
