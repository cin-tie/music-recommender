package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserVectorCacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserVector get(String spotifyId){
        String json = stringRedisTemplate.opsForValue().get("userVector " + spotifyId);

        if(json == null)
            return null;

        try {
            return objectMapper.readValue(json, UserVector.class);
        } catch (Exception e){
            return  null;
        }
    }

    public void save(String spotifyId, UserVector userVector){
        try {
            stringRedisTemplate.opsForValue().set(
                    "userVector " + spotifyId,
                    objectMapper.writeValueAsString(userVector),
                    Duration.ofMinutes(10)
            );
        } catch (Exception e){

        }
    }
}