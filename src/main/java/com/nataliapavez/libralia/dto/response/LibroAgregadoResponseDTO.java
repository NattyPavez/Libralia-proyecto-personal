package com.nataliapavez.libralia.dto.response;

import com.nataliapavez.libralia.domain.model.EstadoLectura;

public record LibroAgregadoResponseDTO(
        Long id,
        String titulo,
        EstadoLectura estadoLectura,
        String nombreUsuario
) {
}
