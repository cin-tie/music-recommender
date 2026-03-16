package com.cintie.musicrecommender.authservice.clients;

import com.cintie.musicrecommender.authservice.dto.UserSyncRequest;
import com.cintie.musicrecommender.authservice.services.ServiceJwtService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient.Builder webClientBuilder;

    private final ServiceJwtService serviceJwtService;

    public void sync(UserSyncRequest request){
        String serviceToken = serviceJwtService.generateServiceToken("auth-service");

        webClientBuilder.build().post()
                .uri("http://user-service/internal/users/sync")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceToken)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
