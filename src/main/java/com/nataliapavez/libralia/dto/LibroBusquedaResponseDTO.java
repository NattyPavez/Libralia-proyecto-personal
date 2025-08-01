package com.nataliapavez.libralia.dto;

public record LibroBusquedaResponseDTO(
        String titulo,
        String autor,
        String genero,
        String descripcion,
        String urlPortada,
        int anioPublicacion,
        Double calificacion
) {
}
