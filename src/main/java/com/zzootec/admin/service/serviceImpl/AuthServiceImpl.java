package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.auth.LoginRequestDto;
import com.zzootec.admin.dto.auth.LoginResponseDto;
import com.zzootec.admin.entity.Usuario;
import com.zzootec.admin.repository.UsuarioRepository;
import com.zzootec.admin.security.jwt.JwtService;
import com.zzootec.admin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        // 1️⃣ Autenticación con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2️⃣ Obtener usuario desde BD
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Credenciales inválidas")
                );

        // 3️⃣ Generar JWT
        String token = jwtService.generateToken(usuario);

        // 4️⃣ Respuesta
        return LoginResponseDto.builder()
                .token(token)
                .tipo("Bearer")
                .idUsuario(usuario.getId())
                .email(usuario.getEmail())
                .nombres(usuario.getNombres())
                .roles(
                        usuario.getRoles()
                                .stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
