package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.response.LibroDTO;
import com.nataliapavez.libralia.service.LibroPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/libro-personal")
public class LibroPersonalController {

    @Autowired
    private LibroPersonalService libroPersonalServicio;

    @GetMapping("/libros")
    public ResponseEntity<List<LibroDTO>> obtenerLibrosUsuario() {
        var libros = libroPersonalServicio.obtenerLibros();

        if (libros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/")
    public ResponseEntity<String> inicio() {
        String mensaje =libroPersonalServicio.inicio();
        return ResponseEntity.ok(mensaje);
    }

}
