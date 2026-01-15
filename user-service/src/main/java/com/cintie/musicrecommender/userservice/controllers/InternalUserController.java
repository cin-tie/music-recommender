package com.cintie.musicrecommender.userservice.controllers;

import com.cintie.musicrecommender.userservice.dto.UserSyncRequest;
import com.cintie.musicrecommender.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @PostMapping("/sync")
    public ResponseEntity<Void> sync(@RequestBody UserSyncRequest userSyncRequest){

        userService.upsertFromSpotify(userSyncRequest);

        return ResponseEntity.ok().build();
    }
}
