package com.nataliapavez.libralia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank String login,
        @NotBlank String password
) {
}
