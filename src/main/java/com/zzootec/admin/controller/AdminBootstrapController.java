package com.zzootec.admin.controller;

import com.zzootec.admin.dto.user.RegisterUserRequestDto;
import com.zzootec.admin.service.RegisterUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bootstrap")
@CrossOrigin(origins = "*")
public class AdminBootstrapController {

    private final RegisterUserService registerUserService;

    public AdminBootstrapController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping("/register")
    public void registerAdminUser(
            @Valid @RequestBody RegisterUserRequestDto request
    ) {
        registerUserService.register(request);
    }
}
