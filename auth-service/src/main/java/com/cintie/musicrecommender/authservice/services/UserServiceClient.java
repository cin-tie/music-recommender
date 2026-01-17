package com.cintie.musicrecommender.authservice.services;

import com.cintie.musicrecommender.authservice.dto.UserSyncRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    private final ServiceJwtService serviceJwtService;

    public void sync(UserSyncRequest request){
        String serviceToken = serviceJwtService.generateServiceToken("auth-service");
        System.out.println("\n\n\n\n\n\nGenerated service token: " + serviceToken);

        webClient.post()
                .uri("http://user-service/internal/users/sync")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceToken)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
