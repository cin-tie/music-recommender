package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.clients.AuthServiceClient;
import com.cintie.musicrecommender.spotifyservice.clients.SpotifyApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getRecentTrackIds(String spotifyId){
        try {
            String json = getRecent(spotifyId);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);

            JsonNode items = jsonNode.get("items");
            if (items == null || !items.isArray()) {
                return List.of();
            }

            List<String> trackIds = new ArrayList<>();

            for (JsonNode item : items){
                JsonNode track = item.get("track");
                if(track != null && track.hasNonNull("id")){
                    trackIds.add(track.get("id").asText());
                }
            }

            return trackIds;
        } catch (Exception e){
            throw new RuntimeException("Failed to parse recent tracks ids", e);
        }
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

    public String getSavedTracks(String spotifyId){
        String cached = spotifyCacheService.getSavedTracks(spotifyId);

        if(cached != null){
            return cached;
        }

        String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
        String data = spotifyApiClient.getSavedTracks(token);

        spotifyCacheService.saveSavedTracks(spotifyId, data);
        return data;
    }

    public String getRecommendations(String spotifyId) {
        String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
        String data = spotifyApiClient.getRecommendations(token);

        return data;
    }

    public List<String> getRecommendationsTrackIds(String spotifyId) {
        try {
            String json = getRecent(spotifyId);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);

            JsonNode tracks = jsonNode.get("tracks");

            List<String> trackIds = new ArrayList<>();

            for (JsonNode track : tracks){
                if(track != null && track.hasNonNull("id")){
                    trackIds.add(track.get("id").asText());
                }
            }

            return trackIds;
        } catch (Exception e){
            throw new RuntimeException("Failed to parse recent tracks ids", e);
        }
    }
}
