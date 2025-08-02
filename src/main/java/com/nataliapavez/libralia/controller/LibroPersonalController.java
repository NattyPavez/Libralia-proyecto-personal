package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.EditarResenaPorTituloLibroLeidoDTO;
import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.service.LibroPersonalService;
import jakarta.validation.Valid;
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
    public List<LibroDTO> obtenerLibrosUsuario() {
        return libroPersonalServicio.obtenerLibros();
    }
    @GetMapping("/")
    public String inicio() {
        return libroPersonalServicio.inicio();
    }

}
