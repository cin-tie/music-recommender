package com.cintie.musicrecommender.recommendationservice.services;

import com.cintie.musicrecommender.recommendationservice.dto.TrackVector;
import com.cintie.musicrecommender.recommendationservice.dto.UserVector;
import org.springframework.stereotype.Service;

@Service
public class SimilarityService {

    private double sq(double v){
        return v * v;
    }

    public double cosine(UserVector userVector, TrackVector trackVector){
        double dot = userVector.getAcousticness() * trackVector.getAcousticness() +
                userVector.getDanceability() * trackVector.getDanceability() +
                userVector.getEnergy() * trackVector.getEnergy() +
                userVector.getInstrumentalness() * trackVector.getInstrumentalness() +
                userVector.getLoudness() * trackVector.getLoudness() +
                userVector.getLiveness() * trackVector.getLiveness() +
                userVector.getSpeechiness() * trackVector.getSpeechiness() +
                userVector.getTempo() * trackVector.getTempo() +
                userVector.getValence() + trackVector.getValence();

        double userVectorNorm = Math.sqrt(sq(userVector.getAcousticness()) +
                sq(userVector.getDanceability()) +
                sq(userVector.getEnergy()) +
                sq(userVector.getInstrumentalness()) +
                sq(userVector.getLoudness()) +
                sq(userVector.getLiveness()) +
                sq(userVector.getSpeechiness()) +
                sq(userVector.getTempo()) +
                sq(userVector.getValence())
        );

        double trackVectorNorm = Math.sqrt(sq(trackVector.getAcousticness()) +
                sq(trackVector.getDanceability()) +
                sq(trackVector.getEnergy()) +
                sq(trackVector.getInstrumentalness()) +
                sq(trackVector.getLoudness()) +
                sq(trackVector.getLiveness()) +
                sq(trackVector.getSpeechiness()) +
                sq(trackVector.getTempo()) +
                sq(trackVector.getValence())
        );

        return dot / (userVectorNorm * trackVectorNorm);
    }
}
