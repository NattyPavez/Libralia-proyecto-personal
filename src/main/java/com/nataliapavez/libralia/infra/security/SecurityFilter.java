package com.nataliapavez.libralia.infra.security;

import com.nataliapavez.libralia.domain.model.usuario.Usuario;
import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import com.nataliapavez.libralia.service.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String token = recuperarToken(request);

        // Solo actuamos si llegó un Bearer token
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String login = tokenService.getSubject(token); // valida firma/expiración y devuelve subject

                // Cargamos el usuario real desde la BD
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException ex) {
                // token inválido/expirado: no autenticamos; endpoints protegidos devolverán 401
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null; // <-- NO EXCEPCIÓN: permite continuar sin token
        }
        return header.replace("Bearer ", "");
    }
}