package com.zzootec.admin.controller;

import com.zzootec.admin.dto.auth.LoginRequestDto;
import com.zzootec.admin.dto.auth.LoginResponseDto;
import com.zzootec.admin.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        return authService.login(request);
    }
}
