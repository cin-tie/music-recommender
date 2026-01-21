package com.cintie.musicrecommender.spotifyservice.utils;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class AudioFeaturesMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static List<AudioFeatures> fromSpotify(String json){
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode featuresArray = root.get("audio_features");

            List<AudioFeatures> result = new ArrayList<>();

            for(JsonNode node : featuresArray){
                if(node.isNull()) continue;

                result.add(AudioFeatures.builder()
                        .trackId(node.get("id").asText())
                        .acousticness(node.get("acousticness").asDouble())
                        .danceability(node.get("danceability").asDouble())
                        .energy(node.get("energy").asDouble())
                        .instrumentalness(node.get("instrumentalness").asDouble())
                        .key(node.get("key").asInt())
                        .liveness(node.get("liveness").asDouble())
                        .loudness(node.get("loudness").asDouble())
                        .mode(node.get("mode").asInt())
                        .speechiness(node.get("speechiness").asDouble())
                        .tempo(node.get("tempo").asDouble())
                        .timeSignature(node.get("time_signature").asInt())
                        .valence(node.get("valence").asDouble())
                        .build());
            }

        } catch (Exception e){
            throw  new RuntimeException("Failed to parse audio features", e);
        }
    }
}
