package com.nataliapavez.libralia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroUsuarioDTO(
        @NotBlank @Email String correo,
        @NotBlank String nombreUsuario,
        Integer edad,
        String descripcion,
        String enlaces,
        @NotBlank @Size(min = 4, max = 50) String login,
        @NotBlank @Size(min = 8, max = 225) String password
) {
}
