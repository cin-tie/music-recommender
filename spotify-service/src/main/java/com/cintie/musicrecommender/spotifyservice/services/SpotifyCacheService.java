package com.cintie.musicrecommender.spotifyservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SpotifyCacheService {

    private final StringRedisTemplate stringRedisTemplate;

    public String getRecent(String spotifyId){
        return stringRedisTemplate.opsForValue().get("recent:" + spotifyId);
    }

    public void saveRecent(String spotifyId, String data) {
        stringRedisTemplate.opsForValue()
                .set("recent:" + spotifyId, data, Duration.ofMinutes(2));
    }
}
