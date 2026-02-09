package com.zzootec.admin.controller;

import com.zzootec.admin.dto.user.UserResponseDto;
import com.zzootec.admin.dto.user.UpdateProfileRequestDto;
import com.zzootec.admin.security.jwt.JwtService;
import com.zzootec.admin.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtService jwtService;

    @GetMapping("/profile")
    public UserResponseDto getProfile(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token no enviado");
        }

        String token = authHeader.substring(7);

        Long userId = jwtService
                .parseClaims(token)
                .getBody()
                .get("userId", Long.class);

        return userService.getUserById(userId);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            HttpServletRequest request,
            @RequestBody @Valid UpdateProfileRequestDto dto
    ) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token no enviado");
        }

        String token = authHeader.substring(7);

        Long userId = jwtService
                .parseClaims(token)
                .getBody()
                .get("userId", Long.class);

        UserResponseDto updated = userService.updateProfileById(userId, dto);
        return ResponseEntity.ok(updated);
    }
}
