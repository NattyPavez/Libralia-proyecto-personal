package com.nataliapavez.libralia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BuscarLibroRequestDTO(
        @NotBlank(message = "La busqueda no puede estar vacia") String textoBusqueda){


}
