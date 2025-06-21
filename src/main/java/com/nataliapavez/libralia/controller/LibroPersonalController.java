package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.service.LibroPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
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
