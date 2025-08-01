package com.nataliapavez.libralia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BuscarLibroRequestDTO(
        @NotBlank(message = "La busqueda no puede estar vacia") String textoBusqueda){


}
