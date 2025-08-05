package com.nataliapavez.libralia.dto.response;

import com.nataliapavez.libralia.domain.model.LibroLibraliaDB;

public record LibroLibraliaDTO(
        String titulo,
        String autor,
        String genero,
        String descripcion,
        String urlPortada,
        int anioDePublicación,
        Double calificacionGoogle
) {
    public static LibroLibraliaDTO desdeEntidad(LibroLibraliaDB libro) {
        return new LibroLibraliaDTO(
                libro.getTitulo(),
                libro.getAutor(),
                libro.getGenero(),
                libro.getDescripcion(),
                libro.getUrlPortada(),
                libro.getAnioDePublicacion(),
                libro.getCalificacionGoogle()
        );
    }
}
