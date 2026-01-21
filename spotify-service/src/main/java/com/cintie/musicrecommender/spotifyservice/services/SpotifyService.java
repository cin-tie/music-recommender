package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.clients.AuthServiceClient;
import com.cintie.musicrecommender.spotifyservice.clients.SpotifyApiClient;
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

    public String getTopTracks(String spotifyId){
        String cached = spotifyCacheService.getTopTracks(spotifyId);

        if(cached != null){
            return cached;
        }

        String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
        String data = spotifyApiClient.getTopTracks(token);

        spotifyCacheService.saveTopTracks(spotifyId, data);
        return data;
    }

    public String getTopArtists(String spotifyId){
        String cached = spotifyCacheService.getTopArtists(spotifyId);

        if(cached != null){
            return cached;
        }

        String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
        String data = spotifyApiClient.getTopArtists(token);

        spotifyCacheService.saveTopArtists(spotifyId, data);
        return data;
    }
}
