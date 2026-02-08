package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.user.RegisterUserRequestDto;
import com.zzootec.admin.entity.Rol;
import com.zzootec.admin.entity.Usuario;
import com.zzootec.admin.repository.RolRepository;
import com.zzootec.admin.repository.UsuarioRepository;
import com.zzootec.admin.service.RegisterUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserServiceImpl(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterUserRequestDto request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Set<Rol> roles = request.getRoles().stream()
                .map(nombre ->
                        rolRepository.findByNombre(nombre)
                                .orElseThrow(() ->
                                        new RuntimeException("Rol no válido: " + nombre)
                                )
                )
                .collect(Collectors.toSet());

        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .roles(roles)
                .build();

        usuarioRepository.save(usuario);
    }
}
