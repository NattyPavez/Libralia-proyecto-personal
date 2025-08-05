package com.nataliapavez.libralia.dto;

import jakarta.validation.constraints.NotBlank;

public record EliminarLibroDTO(@NotBlank String titulo) {
}
