package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import org.springframework.stereotype.Component;

@Component
public class UserVectorNormalizer {

    public UserVector normalize(UserVector vector){
        return UserVector.builder()
                .acousticness(vector.getAcousticness())
                .danceability(vector.getDanceability())
                .energy(vector.getEnergy())
                .instrumentalness(vector.getInstrumentalness())
                .liveness(vector.getLiveness())
                .loudness(normalizeLoudness(vector.getLoudness()))
                .speechiness(vector.getSpeechiness())
                .tempo(normalizeTempo(vector.getTempo()))
                .valence(vector.getValence())
                .build();
    }

    private double normalizeTempo(double tempo){
        return (tempo - 60) / (200 - 60);
    }

    private double normalizeLoudness(double loudness){
        return (loudness + 60) / 60;
    }
}
