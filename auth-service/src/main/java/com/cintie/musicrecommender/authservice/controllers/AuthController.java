package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.ProfileRequest;
import com.cintie.musicrecommender.authservice.dto.ProfileResponse;
import com.cintie.musicrecommender.authservice.dto.ValidateRequest;
import com.cintie.musicrecommender.authservice.dto.ValidateResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtService jwtService;
    private UserRepository userRepository;

    public AuthController() {
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login(){
        return ResponseEntity.status(302)
                .header("Location", "http://localhost:8081/oauth2/authorization/spotify")
                .build();
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request){
        String token = request.token();
        if(jwtService.validateToken(token)){
            return ResponseEntity.ok(new ValidateResponse(true));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(@RequestBody ProfileRequest request){
        String token = request.token();
        if(!jwtService.validateToken(token)){
            return ResponseEntity.badRequest().build();
        }

        try {
            User user = userRepository.findBySpotifyId(jwtService.getSpotifyId(token))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            ProfileResponse response = new ProfileResponse(user.getSpotifyId(), user.getEmail(), user.getDisplayName(), user.getCreatedAt(), user.getUpdatedAt());

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}