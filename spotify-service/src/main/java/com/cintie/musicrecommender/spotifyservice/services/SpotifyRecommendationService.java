package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.clients.AuthServiceClient;
import com.cintie.musicrecommender.spotifyservice.clients.SpotifyApiClient;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyRecommendationService {

    // TODO: Add info to request(for working)

    private final SpotifyService spotifyService;

    public List<TrackVector> getRecommendations(String spotifyId){
        List<String> recommendations = spotifyService.getRecommendationsTrackIds(spotifyId);

        List<TrackVector> result = new ArrayList<>();
        List<TrackVector> missing = new ArrayList<>();


    }
}
