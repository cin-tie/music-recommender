package com.cintie.musicrecommender.userservice.services;

import com.cintie.musicrecommender.userservice.dto.UserSyncRequest;
import com.cintie.musicrecommender.userservice.entities.User;
import com.cintie.musicrecommender.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getBySpotifyId(String spotifyId){
        return userRepository.findBySpotifyId(spotifyId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void upsertFromSpotify(UserSyncRequest userSyncRequest){
        User user = userRepository.findBySpotifyId(userSyncRequest.spotifyId())
                .map(existing -> update(existing, userSyncRequest))
                .orElseGet(() -> create(userSyncRequest));

        userRepository.save(user);
    }

    private User update(User user, UserSyncRequest dto){
        user.setDisplayName(dto.displayName());
        user.setEmail(dto.email());
        user.setCountry(dto.country());
        user.setProduct(dto.product());
        user.setExplicitFilterEnabled(dto.explicitFilterEnabled());
        user.setExplicitFilterLocked(dto.explicitFilterLocked());
        user.setFollowers(dto.followers());
        user.setImageUrl(dto.imageUrl());
        return user;
    }

    private User create(UserSyncRequest dto){
        return User.builder()
                .spotifyId(dto.spotifyId())
                .displayName(dto.displayName())
                .email(dto.email())
                .country(dto.country())
                .product(dto.product())
                .explicitFilterEnabled(dto.explicitFilterEnabled())
                .explicitFilterLocked(dto.explicitFilterLocked())
                .followers(dto.followers())
                .imageUrl(dto.imageUrl())
                .build();
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
