package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.response.LibroLibraliaDTO;
import com.nataliapavez.libralia.service.LibroLibraliaDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros-db") // para campiar a:  /api/libros
public class LibroLibraliaController {

    @Autowired
    private LibroLibraliaDBService libroLibraliaDBService;

    @GetMapping
    public ResponseEntity<Page<LibroLibraliaDTO>> obtenerTodosLosLibros(Pageable pageable) {
        Page<LibroLibraliaDTO> libros = libroLibraliaDBService.obtenerTodosLosLibrosDTO(pageable);

        if (libros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libros);
    }
}
