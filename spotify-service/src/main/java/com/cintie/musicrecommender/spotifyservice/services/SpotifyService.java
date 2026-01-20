package com.cintie.musicrecommender.spotifyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final AuthServiceClient authServiceClient;
    private final SpotifyApiClient spotifyApiClient;
    private final SpotifyCacheService spotifyCacheService;

    public String getRecent(String spotifyId){
        String cached = spotifyCacheService.getRecent(spotifyId);

        if(cached != null){
            return cached;
        }

        String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
        String data = spotifyApiClient.getRecentlyPlayed(token);

        spotifyCacheService.saveRecent(spotifyId, data);
        return data;
    }
}
