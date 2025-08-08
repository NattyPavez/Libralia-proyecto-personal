package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public JpaUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construimos el UserDetails que Spring Security sabe manejar
        return User
                .withUsername(usuario.getLogin())       // username = login
                .password(usuario.getPassword())        // hash BCrypt ya guardado
                .roles(usuario.getRol().name())         // e.g. "USER" -> ROLE_USER
                .accountLocked(!usuario.isActivo())     // si activo=false, cuenta bloqueada
                .build();
    }
}
