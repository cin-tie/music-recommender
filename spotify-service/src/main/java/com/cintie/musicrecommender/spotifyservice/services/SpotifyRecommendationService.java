package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.clients.AuthServiceClient;
import com.cintie.musicrecommender.spotifyservice.clients.SpotifyApiClient;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyRecommendationService {

    private final SpotifyApiClient spotifyApiClient;
    private final AuthServiceClient authServiceClient;
    private final SpotifyRecommendationCacheService spotifyRecommendationCacheService;

    public List<TrackVector> getRecommendations(String spotifyId){
        
    }
}
