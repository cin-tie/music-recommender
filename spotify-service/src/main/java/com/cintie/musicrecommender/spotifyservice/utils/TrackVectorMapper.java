package com.cintie.musicrecommender.spotifyservice.utils;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TrackVectorMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<TrackVector> fromSpotify(String jsonTracks, String jsonAudioFeatures){
        try{
            JsonNode root = objectMapper.readTree(jsonTracks);
            JsonNode trackArray = root.get("tracks");

            List<TrackVector> result = new ArrayList<>();
            List<AudioFeatures> audioFeaturesList = AudioFeaturesMapper.fromSpotify(jsonAudioFeatures);

            int i = 0;
            for(JsonNode node : trackArray){
                if(node.isNull()) continue;

                AudioFeatures features = audioFeaturesList.get(i);

                String trackId = node.get("id").asText();
                if (!trackId.equals(features.getTrackId())){
                    for(AudioFeatures feature : audioFeaturesList){
                        if(trackId.equals(feature.getTrackId())){
                            features = feature;
                            break;
                        }
                    }
                }

                result.add(TrackVector.builder()
                        .trackId(trackId)
                        .name(node.get("name").asText())
                        .artist(node.get("artist").asText())
                        .acousticness(features.getAcousticness())
                        .danceability(features.getDanceability())
                        .energy(features.getEnergy())
                        .instrumentalness(features.getInstrumentalness())
                        .liveness(features.getLiveness())
                        .loudness(features.getLoudness())
                        .speechiness(features.getSpeechiness())
                        .tempo(features.getTempo())
                        .valence(features.getValence())
                        .build());
                ++i;
            }

            return result;
        } catch (Exception e){
            throw new RuntimeException("Failed to parse track vector", e);
        }
    }
}
