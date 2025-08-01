package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.AgregarLibroRequestDTO;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.service.BibliotecaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarLibroABiblioteca(@RequestBody @Valid AgregarLibroRequestDTO request) {
        try {
            bibliotecaService.agregarLibroDesdeDTO(request);
            return ResponseEntity.ok("📚 El libro '" + request.libro().titulo() + "' fue agregado a tu biblioteca (" + request.estadoLectura() + ").");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Error inesperado al agregar el libro: " + e.getMessage());
        }
    }
}