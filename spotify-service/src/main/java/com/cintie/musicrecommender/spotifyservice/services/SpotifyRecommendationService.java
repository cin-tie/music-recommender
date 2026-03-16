package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import com.cintie.musicrecommender.spotifyservice.utils.TrackVectorMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyRecommendationService {

    // TODO: Add info to request(for working)

    private final SpotifyService spotifyService;
    private final AudioFeaturesService audioFeaturesService;
    private final SpotifyRecommendationCacheService spotifyRecommendationCacheService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<TrackVector> getRecommendations(String spotifyId){
        List<String> seedTracks = spotifyService.getRecentTrackIds(spotifyId);
        List<String> listArtists = spotifyService.getTopArtistIds(spotifyId);
        List<String> recommendations = spotifyService.getRecommendationsTrackIds(spotifyId, seedTracks, listArtists);
        List<String> missed = new ArrayList<>();

        List<TrackVector> result = new ArrayList<>();

        for(String trackId : recommendations){
            TrackVector trackVector = spotifyRecommendationCacheService.get(trackId);

            if(trackVector == null){
                missed.add(trackId);
            }
            else{
                result.add(trackVector);
            }
        }

        List<AudioFeatures> features = audioFeaturesService.getForTracks(spotifyId, missed);
        List<String> tracks = spotifyService.getTracks(spotifyId, missed);

        int i = 0;
        try {
            for (String track : tracks) {
                JsonNode trackNode = objectMapper.readTree(track);
                String trackId = trackNode.get("id").asText();

                AudioFeatures feature = features.get(i);

                if (feature.getTrackId() != trackId) {
                    for (AudioFeatures audioFeatures : features) {
                        if (audioFeatures.getTrackId() == trackId) {
                            feature = audioFeatures;
                            break;
                        }
                    }
                }

                TrackVector trackVector = TrackVectorMapper.fromSpotify(trackNode, feature);

                result.add(trackVector);

                ++i;
            }

            return result;
        } catch (Exception e){
            throw new RuntimeException("Failed to parse tracks", e);
        }
    }
}
