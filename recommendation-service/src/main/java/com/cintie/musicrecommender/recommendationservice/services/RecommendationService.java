package com.cintie.musicrecommender.recommendationservice.services;

import com.cintie.musicrecommender.recommendationservice.clients.SpotifyServiceClient;
import com.cintie.musicrecommender.recommendationservice.dto.RecommendationResponse;
import com.cintie.musicrecommender.recommendationservice.dto.TrackVector;
import com.cintie.musicrecommender.recommendationservice.dto.UserVector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final SimilarityService similarityService;
    private final SpotifyServiceClient spotifyServiceClient;

    public List<RecommendationResponse> recommendations(String spotifyId){
        UserVector userVector = spotifyServiceClient.getUserVector(spotifyId);
        List<TrackVector> recommendations = spotifyServiceClient.getRecommendations(spotifyId);

        return recommendations.stream()
                .map(trackVector -> RecommendationResponse.builder()
                        .trackId(trackVector.getTrackId())
                        .name(trackVector.getName())
                        .artist(trackVector.getArtist())
                        .similarity(similarityService.cosine(userVector, trackVector))
                        .build())
                .sorted((a, b) -> Double.compare(b.getSimilarity(), a.getSimilarity()))
                .limit(3)
                .toList();
    }
}
