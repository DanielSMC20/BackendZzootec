package com.zzootec.admin.service;

import com.zzootec.admin.dto.user.CreateUserRequestDto;
import com.zzootec.admin.dto.user.UpdateProfileRequestDto;
import com.zzootec.admin.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto request);
    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, CreateUserRequestDto request);

    void deleteUser(Long id);
    UserResponseDto getProfile(String email);

    UserResponseDto updateProfileById(
            Long userId,
            UpdateProfileRequestDto dto
    );
    UserResponseDto getUserById(Long id);

}
