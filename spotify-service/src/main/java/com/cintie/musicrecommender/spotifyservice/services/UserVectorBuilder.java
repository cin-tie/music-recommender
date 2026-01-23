package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserVectorBuilder {

    public UserVector build(List<AudioFeatures> features){
        int n = features.size();

        if(n == 0){
            throw  new IllegalArgumentException("No audio features provided");
        }

        double acousticness = 0;
        double danceability = 0;
        double energy = 0;
        double instrumentalness = 0;
        double liveness = 0;
        double loudness = 0;
        double speechiness = 0;
        double tempo = 0;
        double valence = 0;

        for(AudioFeatures feature : features){
            acousticness += feature.getAcousticness();
            danceability += feature.getDanceability();
            energy += feature.getEnergy();
            instrumentalness += feature.getInstrumentalness();
            liveness += feature.getLiveness();
            loudness += feature.getLoudness();
            speechiness += feature.getSpeechiness();
            tempo += feature.getTempo();
            valence += feature.getValence();
        }

        return UserVector.builder()
                .acousticness(acousticness / n)
                .danceability(danceability / n)
                .energy(energy / n)
                .instrumentalness(instrumentalness / n)
                .liveness(liveness / n)
                .loudness(loudness / n)
                .speechiness(speechiness / n)
                .tempo(tempo / n)
                .valence(valence / n)
                .build();
    }
}
