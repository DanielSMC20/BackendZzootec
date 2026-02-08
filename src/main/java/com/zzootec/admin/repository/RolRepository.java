package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombre(String nombre);

    List<Rol> findByNombreIn(List<String> nombres);

}
