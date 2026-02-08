package com.zzootec.admin.service;

import com.zzootec.admin.dto.auth.LoginRequestDto;
import com.zzootec.admin.dto.auth.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
}
