package com.nataliapavez.libralia.dto.response;

import com.nataliapavez.libralia.domain.model.LibroPersonal;

public record LibroDTO(
        String titulo,
        String autor,
        String genero,
        String descripcion,
        String urlPortada,
        String anioDePublicacion,
        String reseniaPersonal,
        Double calificacionGoogle,
        Double calificacionPersonal,
        String estadoLectura
) {
    public LibroDTO(LibroPersonal libro) {
        this(
                libro.getTitulo(),
                libro.getAutor(),
                libro.getGenero(),
                libro.getDescripcionGoogle(),
                libro.getUrlPortada(),
                String.valueOf(libro.getAnioDePublicacion()),
                libro.getReseniaPersonal(),
                libro.getCalificacionGoogle(),
                libro.getCalificacionPersonal(),
                libro.getEstadoLectura().toString()
        );
    }
}

