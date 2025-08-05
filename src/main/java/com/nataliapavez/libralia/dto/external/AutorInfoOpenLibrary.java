package com.nataliapavez.libralia.dto.external;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorInfoOpenLibrary(
        @JsonAlias ("name") String nombre,
        @JsonProperty("birth_date") String anioDeNacimiento,
        @JsonProperty("death_date") String anioDeMuerte,
        Bio bio
        // El campo "bio" es un objeto anidado en el JSON, no un texto plano. Por eso usamos un subrecord.
) {
    // Subrecord que representa el campo "bio", que viene como un objeto con una sola propiedad: "value"
    public record Bio(String value){}
}
