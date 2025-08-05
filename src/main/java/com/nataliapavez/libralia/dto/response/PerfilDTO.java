package com.nataliapavez.libralia.dto.response;

import java.util.List;

public record PerfilDTO(
        UsuarioDTO usuario,
        List<LibroDTO> librosLeidos,
        List<LibroDTO> librosPorLeer,
        List<LibroDTO> librosLeyendo
) {
}
