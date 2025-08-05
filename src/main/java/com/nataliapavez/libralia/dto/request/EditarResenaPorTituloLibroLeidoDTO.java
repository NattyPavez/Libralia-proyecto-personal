package com.nataliapavez.libralia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarResenaPorTituloLibroLeidoDTO(
        @NotBlank(message = "El título no puede estar vacío")
        String titulo,

        @NotBlank(message = "La reseña no puede estar vacía")
        String resenaPersonal,

        @NotNull(message = "La calificación es obligatoria")
        @Min(value = 1, message = "La calificación mínima es 1")
        @Max(value = 5, message = "La calificación máxima es 5")
        Double calificacionPersonal
) {}
