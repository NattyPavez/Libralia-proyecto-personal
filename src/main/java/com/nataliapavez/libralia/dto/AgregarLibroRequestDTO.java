package com.nataliapavez.libralia.dto;

public record AgregarLibroRequestDTO( String nombreUsuario,
                                      LibroGoogleDTO libro,
                                      String estadoLectura)// "LEIDO", "LEYENDO", "POR_LEER")
{}
