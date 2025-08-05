package com.nataliapavez.libralia.dto;

import com.nataliapavez.libralia.domain.model.LibroPersonal;

public record ResenaYCalificacionDeLibroPorUsuarioDTO(String titulo,
                                                      String autor,
                                                      String reseniaPersonal,
                                                      String calificacionPersonal) {

    public ResenaYCalificacionDeLibroPorUsuarioDTO(LibroPersonal libro) {
        this(
                libro.getTitulo(),
                libro.getAutor(),
                libro.getReseniaPersonal(),
                libro.getCalificacionPersonal() != null ? String.valueOf(libro.getCalificacionPersonal()) : "No calificado");

    }
}