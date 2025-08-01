package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.BuscarLibroRequestDTO;
import com.nataliapavez.libralia.dto.LibroBusquedaResponseDTO;
import com.nataliapavez.libralia.dto.LibroGoogleDTO;
import com.nataliapavez.libralia.dto.VolumeInfo;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.service.LibroBusquedaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroBusquedaController {

    @Autowired
    private LibroBusquedaService libroBusquedaService;

    public LibroBusquedaController(LibroBusquedaService libroBusquedaService) {
        this.libroBusquedaService = libroBusquedaService;
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarLibros(@RequestBody @Valid BuscarLibroRequestDTO request) {
        try {
            List<LibroBusquedaResponseDTO> resultados = libroBusquedaService.buscarLibrosDesdeApi(request);
            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron resultados.");
            }
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar libros: " + e.getMessage());
        }
    }
}

