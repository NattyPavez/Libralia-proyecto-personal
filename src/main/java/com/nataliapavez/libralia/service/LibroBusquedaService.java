package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.api.ConsumoAPI;
import com.nataliapavez.libralia.dto.*;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.model.LibroLibraliaDB;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.repository.LibroLibraliaDBRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LibroBusquedaService {

    private final LibroLibraliaDBRepository libroLibraliaDBRepositorio;

    public LibroBusquedaService(LibroLibraliaDBRepository libroLibraliaDBRepositorio) {
        this.libroLibraliaDBRepositorio = libroLibraliaDBRepositorio;
    }

    public List<LibroBusquedaResponseDTO> buscarLibrosDesdeApi(BuscarLibroRequestDTO request) throws IOException, InterruptedException {
        String textoBusqueda = request.textoBusqueda().trim();
        EstadoLectura estadoLectura = EstadoLectura.valueOf(request.estadoLectura().toUpperCase());

        if (textoBusqueda.isEmpty()) {
            throw new IllegalArgumentException("La búsqueda no puede estar vacía");
        }

        var consumoApi = new ConsumoAPI();
        var conversor = new ConversorLibroService();
        final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
        String busquedaFormateada = URLEncoder.encode(textoBusqueda, StandardCharsets.UTF_8);

        String json = consumoApi.obtenerDatos(URL_BASE + busquedaFormateada);
        ResultadoGoogle resultado = conversor.convertir(json, ResultadoGoogle.class);

        if (resultado.items() == null || resultado.items().isEmpty()) {
            return Collections.emptyList();
        }

        List<LibroGoogleDTO> listaOriginal = resultado.items().stream()
                .map(ItemGoogle::getVolumeInfo)
                .filter(Objects::nonNull)
                .map(LibroGoogleDTO::desdeVolumeInfo)
                .toList();

        // Reordenar: primero los que coincidan con el texto de búsqueda
        String texto = textoBusqueda.toLowerCase();

        List<LibroGoogleDTO> coincidenciasAltas = listaOriginal.stream()
                .filter(libro ->
                        libro.titulo().toLowerCase().contains(texto) ||
                                libro.autor().toLowerCase().contains(texto))
                .toList();

        List<LibroGoogleDTO> otrasSugerencias = listaOriginal.stream()
                .filter(libro -> !coincidenciasAltas.contains(libro))
                .toList();

        List<LibroGoogleDTO> listaFinal = Stream.concat(coincidenciasAltas.stream(), otrasSugerencias.stream())
                .limit(10)
                .toList();

        return listaFinal.stream()
                .map(dto -> new LibroBusquedaResponseDTO(
                        dto.titulo(),
                        dto.autor(),
                        dto.genero(),
                        dto.descripcion(),
                        dto.urlPortada(),
                        dto.anioPublicacion(),
                        dto.calificacionGoogle()))
                .collect(Collectors.toList());
    }
}