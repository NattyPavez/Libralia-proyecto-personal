package com.nataliapavez.libralia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EliminarLibroDTO(@NotBlank String titulo) {
}
