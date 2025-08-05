package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.api.ConsumoAPI;
import com.nataliapavez.libralia.domain.repository.LibroLibraliaDBRepository;
import com.nataliapavez.libralia.dto.external.ItemGoogle;
import com.nataliapavez.libralia.dto.external.LibroGoogleDTO;
import com.nataliapavez.libralia.dto.external.ResultadoGoogle;
import com.nataliapavez.libralia.dto.request.BuscarLibroRequestDTO;
import com.nataliapavez.libralia.dto.response.LibroBusquedaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

@Service
public class LibroBusquedaService {

    private final LibroLibraliaDBRepository libroLibraliaDBRepositorio;

    public LibroBusquedaService(LibroLibraliaDBRepository libroLibraliaDBRepositorio) {
        this.libroLibraliaDBRepositorio = libroLibraliaDBRepositorio;
    }

    public Page<LibroBusquedaResponseDTO> buscarLibrosDesdeApi(BuscarLibroRequestDTO request, Pageable pageable) throws IOException, InterruptedException {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int startIndex = page * size;

        String textoBusqueda = request.textoBusqueda().trim();

        if (textoBusqueda.isEmpty()) {
            throw new IllegalArgumentException("La búsqueda no puede estar vacía");
        }

        var consumoApi = new ConsumoAPI();
        var conversor = new ConversorLibroService();
        final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
        String busquedaFormateada = URLEncoder.encode(textoBusqueda, StandardCharsets.UTF_8);

        String url = URL_BASE + busquedaFormateada +
                "&startIndex=" + startIndex +
                "&maxResults=" + size;

        String json = consumoApi.obtenerDatos(url);
        ResultadoGoogle resultado = conversor.convertir(json, ResultadoGoogle.class);

        if (resultado.items() == null || resultado.items().isEmpty()) {
            return Page.empty();
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
                .toList();


        List<LibroBusquedaResponseDTO> respuesta = listaFinal.stream()
                .map(dto -> new LibroBusquedaResponseDTO(
                        dto.titulo(),
                        dto.autor(),
                        dto.genero(),
                        dto.descripcion(),
                        dto.urlPortada(),
                        dto.anioPublicacion(),
                        dto.calificacionGoogle() == null || dto.calificacionGoogle() == 0.0 ? null : dto.calificacionGoogle()))
                .toList();
        long totalEstimado = 100;


        return new PageImpl<>(respuesta, pageable, totalEstimado);
    }
}