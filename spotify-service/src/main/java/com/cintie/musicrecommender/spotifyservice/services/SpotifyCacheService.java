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
                .set("recent:" + spotifyId, data, Duration.ofMinutes(1));
    }

    public String getTopTracks(String spotifyId){
        return stringRedisTemplate.opsForValue().get("topTracks: " + spotifyId);
    }

    public String getTopArtists(String spotifyId){
        return stringRedisTemplate.opsForValue().get("topArtists: " + spotifyId);
    }

    public void saveTopTracks(String spotifyId, String data) {
        stringRedisTemplate.opsForValue()
                .set("topTracks:" + spotifyId, data, Duration.ofMinutes(1));
    }

    public void saveTopArtists(String spotifyId, String data) {
        stringRedisTemplate.opsForValue()
                .set("topArtists:" + spotifyId, data, Duration.ofMinutes(1));
    }
}
