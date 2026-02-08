package com.zzootec.admin.config;

import com.zzootec.admin.entity.Rol;
import com.zzootec.admin.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleInitializer {

    private final RolRepository rolRepository;

    public RoleInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @PostConstruct
    public void initRoles() {

        List<String> roles = List.of(
                "ADMIN",
                "VENTAS",
                "ALMACEN"
        );

        for (String roleName : roles) {
            rolRepository.findByNombre(roleName)
                    .orElseGet(() -> rolRepository.save(
                            Rol.builder().nombre(roleName).build()
                    ));
        }

        System.out.println("✅ Roles verificados/creados");
    }
}
