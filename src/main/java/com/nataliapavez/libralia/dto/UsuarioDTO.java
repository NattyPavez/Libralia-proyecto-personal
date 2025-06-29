package com.nataliapavez.libralia.dto;

import com.nataliapavez.libralia.model.Usuario;

public record UsuarioDTO(
        String nombreUsuario,
        int edad,
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
