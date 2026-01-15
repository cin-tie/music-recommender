package com.cintie.musicrecommender.authservice.services;

import com.cintie.musicrecommender.authservice.dto.UserSyncRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    public void sync(UserSyncRequest request){
        webClient.post()
                .uri("http://user-service/internal/users/sync")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
