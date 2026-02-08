package com.zzootec.admin.controller;

import com.zzootec.admin.dto.user.CreateUserRequestDto;
import com.zzootec.admin.dto.user.UserResponseDto;
import com.zzootec.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // =========================
    // CREAR USUARIO
    // =========================
    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody CreateUserRequestDto request
    ) {
        return userService.createUser(request);
    }

    // =========================
    // LISTAR USUARIOS
    // =========================
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // =========================
    // ACTUALIZAR USUARIO
    // =========================
    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserRequestDto request
    ) {
        return userService.updateUser(id, request);
    }

    // =========================
    // DESACTIVAR USUARIO (borrado lógico)
    // =========================
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}

