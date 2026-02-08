package com.zzootec.admin.repository;
import com.zzootec.admin.dto.user.UpdateProfileRequestDto;
import com.zzootec.admin.dto.user.UserResponseDto;
import com.zzootec.admin.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> getProfile(String email);
    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> getUserById(Long id);


}

