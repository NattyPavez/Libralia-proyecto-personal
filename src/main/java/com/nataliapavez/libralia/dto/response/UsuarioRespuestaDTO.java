package com.nataliapavez.libralia.dto.response;

import com.nataliapavez.libralia.domain.model.usuario.Rol;

public record UsuarioRespuestaDTO(
        Long id,
        String nombreUsuario,
        String correo,
        int edad,
        String descripcion,
        String enlaces,
        String avatarUrl,
        Rol rol,
        boolean activo
) {
}
