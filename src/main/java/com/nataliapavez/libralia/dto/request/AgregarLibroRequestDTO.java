package com.nataliapavez.libralia.dto.request;

import com.nataliapavez.libralia.domain.model.EstadoLectura;
import com.nataliapavez.libralia.dto.external.LibroGoogleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgregarLibroRequestDTO(
        @NotBlank(message = "El nombre de usuario dede ser válido") String nombreUsuario,

        @Valid LibroGoogleDTO libro,

        @NotNull(message = "El estado de lectura debe ser: LEIDO, LEYENDO o POR LEER") EstadoLectura estadoLectura)// "LEIDO", "LEYENDO", "POR_LEER")
{}
