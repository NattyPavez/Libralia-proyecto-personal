package com.nataliapavez.libralia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroGoogleDto (VolumeInfo volumeInfo){
}
