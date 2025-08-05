package com.nataliapavez.libralia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoGoogle (List<ItemGoogle> items) {

}
