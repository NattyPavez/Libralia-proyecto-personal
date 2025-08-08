package com.nataliapavez.libralia.dto.response;

import com.nataliapavez.libralia.domain.model.usuario.Usuario;

public record UsuarioDTO(
        String nombreUsuario,
        Integer edad,
        String descripcion,
        String enlaces,
        String avatarUrl
) {
    public UsuarioDTO(Usuario usuario){
        this(
                usuario.getNombreUsuario(),
                usuario.getEdad(),
                usuario.getDescripcion().orElse(null),
                usuario.getEnlaces().orElse(null),
                usuario.getAvatarUrl()
        );
    }
}
