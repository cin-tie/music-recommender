package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SpotifyRecommendationCacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrackVector get(String trackId){
        String json = stringRedisTemplate.opsForValue().get("trackVector: " + trackId);
        if(json == null)
            return null;

        try{
            return objectMapper.readValue(json, TrackVector.class);
        } catch (Exception e){
            return null;
        }
    }

    public void save(TrackVector trackVector){
        try{
            stringRedisTemplate.opsForValue().set(
                    "trackVector: " + trackVector.getTrackId(),
                    objectMapper.writeValueAsString(trackVector),
                    Duration.ofHours(1)
            );
        } catch (Exception e){}
    }
}
