package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AudioFeaturesCacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AudioFeatures get(String trackId){
        String json = stringRedisTemplate.opsForValue().get("audioFeature: " + trackId);
        if(json == null)
            return null;

        try{
            return objectMapper.readValue(json, AudioFeatures.class);
        } catch (Exception e){
            return null;
        }
    }

    public void save(AudioFeatures audioFeatures){
        try{
            stringRedisTemplate.opsForValue().set(
                    "audioFeatures: " + audioFeatures.getTrackId(),
                    objectMapper.writeValueAsString(audioFeatures),
                    Duration.ofHours(24)
            );
        } catch (Exception e){}
    }
}
