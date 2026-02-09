package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.user.CreateUserRequestDto;
import com.zzootec.admin.dto.user.UpdateProfileRequestDto;
import com.zzootec.admin.dto.user.UserResponseDto;
import com.zzootec.admin.entity.Rol;
import com.zzootec.admin.entity.Usuario;
import com.zzootec.admin.repository.RolRepository;
import com.zzootec.admin.repository.UsuarioRepository;
import com.zzootec.admin.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        List<Rol> roles = request.getRoles().stream()
                .map(nombreRol ->
                        rolRepository.findByNombre(nombreRol)
                                .orElseThrow(() ->
                                        new RuntimeException("Rol no existe: " + nombreRol)
                                )
                )
                .collect(Collectors.toList());

        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .fechaNacimiento(request.getFechaNacimiento())
                .imageUrl(request.getImageUrl())
                .activo(true)
                .roles(roles.stream().collect(Collectors.toSet()))
                .build();

        Usuario saved = usuarioRepository.save(usuario);

        return UserResponseDto.builder()
                .id(saved.getId())
                .nombres(saved.getNombres())
                .apellidos(saved.getApellidos())
                .email(saved.getEmail())
                .telefono(saved.getTelefono())
                .fechaNacimiento(saved.getFechaNacimiento())
                .imageUrl(saved.getImageUrl())
                .activo(saved.isActivo())
                .roles(
                        saved.getRoles().stream()
                                .map(Rol::getNombre)
                                .collect(Collectors.toSet())
                )
                .build();
    }
        @Override
    public List<UserResponseDto> getAllUsers() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserResponseDto updateUser(Long id, CreateUserRequestDto request) {

        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getNombres() != null) {
            user.setNombres(request.getNombres());
        }

        if (request.getApellidos() != null) {
            user.setApellidos(request.getApellidos());
        }

        if (request.getTelefono() != null) {
            user.setTelefono(request.getTelefono());
        }

        if (request.getFechaNacimiento() != null) {
            user.setFechaNacimiento(request.getFechaNacimiento());
        }

        if (request.getImageUrl() != null) {
            user.setImageUrl(request.getImageUrl());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getActivo() != null) {
            user.setActivo(request.getActivo());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Rol> roles = rolRepository
                    .findByNombreIn(request.getRoles())
                    .stream()
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        usuarioRepository.save(user);
        return mapToDto(user);
    }


    @Override
    public void deleteUser(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setActivo(false);
        usuarioRepository.save(user);
    }

    private UserResponseDto mapToDto(Usuario user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .fechaNacimiento(user.getFechaNacimiento())
                .imageUrl(user.getImageUrl())
                .activo(user.isActivo())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toSet())
                )
                .build();
    }
    // En tu Service
    @Override
    public UserResponseDto getProfile(String email) {
        Usuario user = usuarioRepository.getProfile(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToUserResponseDto(user);
    }

    private UserResponseDto mapToUserResponseDto(Usuario user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .fechaNacimiento(user.getFechaNacimiento())
                .imageUrl(user.getImageUrl())
                .activo(user.isActivo())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toSet())
                )
                .build();
    }



    @Override
    @Transactional
    public UserResponseDto updateProfileById(
            Long userId,
            UpdateProfileRequestDto dto
    ) {

        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getNombres() != null) {
            user.setNombres(dto.getNombres());
        }
        if (dto.getApellidos() != null) {
            user.setApellidos(dto.getApellidos());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getTelefono() != null) {
            user.setTelefono(dto.getTelefono());
        }
        if (dto.getFechaNacimiento() != null) {
            user.setFechaNacimiento(java.time.LocalDate.parse(dto.getFechaNacimiento()));
        }
        if (dto.getImageUrl() != null) {
            user.setImageUrl(dto.getImageUrl());
        }

        usuarioRepository.save(user);

        return mapToDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UserResponseDto.builder()
                .id(user.getId())
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .fechaNacimiento(user.getFechaNacimiento())
                .imageUrl(user.getImageUrl())
                .activo(user.isActivo())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toSet())
                )
                .build();
    }


}
