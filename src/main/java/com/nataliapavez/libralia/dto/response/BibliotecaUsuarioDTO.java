package com.nataliapavez.libralia.dto;

import com.nataliapavez.libralia.domain.model.EstadoLectura;
import com.nataliapavez.libralia.domain.model.Usuario;

import java.util.List;

public record BibliotecaUsuarioDTO(
        String nombreUsuario,
        String avatarUrl,
        List<LibroDTO> librosPorLeer,
        List<LibroDTO> librosLeidos,
        List<LibroDTO> librosLeyendo
) {
    public BibliotecaUsuarioDTO(Usuario usuario) {
        this(
                usuario.getNombreUsuario(),
                usuario.getAvatarUrl(),
                usuario.getLibrosPorEstado(EstadoLectura.POR_LEER).stream().map(LibroDTO::new).toList(),
                usuario.getLibrosPorEstado(EstadoLectura.LEIDO).stream().map(LibroDTO::new).toList(),
                usuario.getLibrosPorEstado(EstadoLectura.LEYENDO).stream().map(LibroDTO::new).toList()
        );
    }
}

