package com.zzootec.admin.controller;

import com.zzootec.admin.dto.user.UserResponseDto;
import com.zzootec.admin.dto.user.UpdateProfileRequestDto;
import com.zzootec.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserResponseDto getProfile(Authentication authentication) {
        String email = authentication.getName();
        return userService.getProfile(email);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @RequestParam String email,
            @RequestBody @Valid UpdateProfileRequestDto request) {

        UserResponseDto updatedProfile = userService.updateProfile(email, request);
        return ResponseEntity.ok(updatedProfile);
    }
}