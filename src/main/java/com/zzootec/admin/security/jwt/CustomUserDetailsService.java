    package com.zzootec.admin.security.jwt;

    import com.zzootec.admin.entity.Usuario;
    import com.zzootec.admin.repository.UsuarioRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.*;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class CustomUserDetailsService implements UserDetailsService {

        private final UsuarioRepository usuarioRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return new org.springframework.security.core.userdetails.User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.isActivo(),
                    true,
                    true,
                    true,
                    usuario.getRoles().stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre()))
                            .toList()
            );
        }
    }