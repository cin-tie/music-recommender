package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.clients.AuthServiceClient;
import com.cintie.musicrecommender.spotifyservice.clients.SpotifyApiClient;
import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.utils.AudioFeaturesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AudioFeaturesService {

    private final SpotifyApiClient spotifyApiClient;
    private final AuthServiceClient authServiceClient;
    private final AudioFeaturesCacheService audioFeaturesCacheService;

    public List<AudioFeatures> getForTracks(String spotifyId, List<String> trackIds){
        List<AudioFeatures> result = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        for (String id : trackIds) {
            AudioFeatures cached = audioFeaturesCacheService.get(id);
            if (cached != null) {
                result.add(cached);
            } else {
                missing.add(id);
            }
        }

        System.out.println("8");
        if(!missing.isEmpty()){
            String token = authServiceClient.getSpotifyAccessToken(spotifyId).accessToken();
            System.out.println("9");
            String data = spotifyApiClient.getAudioFeatures(token, missing);

            List<AudioFeatures> fetched = AudioFeaturesMapper.fromSpotify(data);
            fetched.forEach(audioFeaturesCacheService::save);
            result.addAll(fetched);
        }
        return result;
    }
}
