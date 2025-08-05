package com.nataliapavez.libralia.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)

public record LibroGoogleDTO(
        @NotBlank String titulo,
        @NotBlank String autor,
        @NotBlank String descripcion,
        @NotBlank String urlPortada,
        @NotBlank String genero,
        @NotNull Integer anioPublicacion,
        @Nullable Double calificacionGoogle
) {
    public static LibroGoogleDTO desdeVolumeInfo(VolumeInfo info) {
        String titulo = Optional.ofNullable(info.getTitle()).orElse("Título desconocido");

        String autor = Optional.ofNullable(info.getAuthors())
                .filter(list -> !list.isEmpty())
                .map(list -> String.join(", ", list))
                .orElse("Autor desconocido");

        String descripcion = Optional.ofNullable(info.getDescription())
                .orElse("Sin descripción disponible");

        String urlPortada = Optional.ofNullable(info.getImageLinks())
                .map(ImageLinks::getSmallThumbnail)
                .orElse("Sin imagen disponible");

        String genero = Optional.ofNullable(info.getCategories())
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .orElse("Género no especificado");

        Integer anio = Optional.ofNullable(info.getPublishedDate())
                .filter(f -> f.length() >= 4)
                .map(f -> {
                    try {
                        return Integer.parseInt(f.substring(0, 4));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }).orElse(0);

        Double rating = Optional.ofNullable(info.getAverageRating())
                .filter(r -> r != 0.0) // si es 0.0 también lo transformamos en null
                .orElse(null);

        return new LibroGoogleDTO(titulo, autor, descripcion, urlPortada, genero, anio, rating);
    }

    public VolumeInfo volumeInfo() {
        ImageLinks links = new ImageLinks();
        links.setSmallThumbnail(Optional.ofNullable(urlPortada).orElse("Sin imagen disponible"));

        VolumeInfo info = new VolumeInfo();
        info.setTitle(Optional.ofNullable(titulo).orElse("Título desconocido"));
        info.setAuthors(Optional.ofNullable(autor).map(List::of).orElse(List.of("Autor desconocido")));
        info.setDescription(Optional.ofNullable(descripcion).orElse("Sin descripción disponible"));
        info.setImageLinks(links);
        info.setCategories(Optional.ofNullable(genero).map(List::of).orElse(List.of("Género no especificado")));
        info.setPublishedDate(Optional.ofNullable(anioPublicacion)
                .map(String::valueOf)
                .orElse("Fecha desconocida"));
        info.setAverageRating(Optional.ofNullable(calificacionGoogle).orElse(0.0));

        return info;

    }
}
