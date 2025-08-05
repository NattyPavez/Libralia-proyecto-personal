package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.request.BuscarLibroRequestDTO;
import com.nataliapavez.libralia.dto.response.LibroBusquedaResponseDTO;
import com.nataliapavez.libralia.service.LibroBusquedaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
public class LibroBusquedaController {

    private LibroBusquedaService libroBusquedaService;

    public LibroBusquedaController(LibroBusquedaService libroBusquedaService) {
        this.libroBusquedaService = libroBusquedaService;
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarLibros(@RequestBody @Valid BuscarLibroRequestDTO request, Pageable paginacion) {
        try {
            Page<LibroBusquedaResponseDTO> resultados = libroBusquedaService.buscarLibrosDesdeApi(request, paginacion);

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron resultados para la búsqueda.");
            }

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }
}
