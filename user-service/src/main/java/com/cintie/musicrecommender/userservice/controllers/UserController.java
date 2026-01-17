package com.cintie.musicrecommender.userservice.controllers;

import com.cintie.musicrecommender.userservice.dto.UserProfileResponse;
import com.cintie.musicrecommender.userservice.entities.User;
import com.cintie.musicrecommender.userservice.services.UserService;
import com.cintie.musicrecommender.userservice.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse me(@RequestHeader("X-User-Id") String sporifyId){
        User user = userService.getBySpotifyId(sporifyId);
        return UserMapper.map(user);
    }

}
