package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.ProfileRequest;
import com.cintie.musicrecommender.authservice.dto.ProfileResponse;
import com.cintie.musicrecommender.authservice.dto.ValidateRequest;
import com.cintie.musicrecommender.authservice.dto.ValidateResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.UserJwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserJwtService userJwtService;
    private UserRepository userRepository;

    @GetMapping("/login")
    public ResponseEntity<Void> login(){
        return ResponseEntity.status(302)
                .header("Location", "http://localhost:8081/oauth2/authorization/spotify")
                .build();
    }
}