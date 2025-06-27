package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.dto.LibroLibraliaDTO;
import com.nataliapavez.libralia.service.LibroLibraliaDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/libros-db") // para campiar a:  /api/libros
public class LibroLibraliaController {

    @Autowired
    private LibroLibraliaDBService libroLibraliaDBService;

    @GetMapping
    public List<LibroLibraliaDTO> obtenerTodosLosLibros() {
        return libroLibraliaDBService.obtenerTodosLosLibrosDTO();
    }
}
