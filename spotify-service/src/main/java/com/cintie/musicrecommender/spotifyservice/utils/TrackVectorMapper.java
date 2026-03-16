package com.cintie.musicrecommender.spotifyservice.utils;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TrackVectorMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TrackVector fromSpotify(JsonNode trackNode, AudioFeatures features){
        if(trackNode.isNull()) return null;

        return  TrackVector.builder()
                .trackId(trackNode.get("id").asText())
                .name(trackNode.get("name").asText())
                .artist(trackNode.get("artist").asText())
                .acousticness(features.getAcousticness())
                .danceability(features.getDanceability())
                .energy(features.getEnergy())
                .instrumentalness(features.getInstrumentalness())
                .liveness(features.getLiveness())
                .loudness(features.getLoudness())
                .speechiness(features.getSpeechiness())
                .tempo(features.getTempo())
                .valence(features.getValence())
                .build();
    }
}
