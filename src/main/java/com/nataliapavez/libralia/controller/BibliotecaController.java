package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.request.AgregarLibroRequestDTO;
import com.nataliapavez.libralia.dto.response.LibroAgregadoResponseDTO;
import com.nataliapavez.libralia.service.BibliotecaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<LibroAgregadoResponseDTO> agregarLibroABiblioteca(
            @RequestBody @Valid AgregarLibroRequestDTO request,
            UriComponentsBuilder uriBuilder) {
        try {
            var libro = bibliotecaService.agregarLibroDesdeDTO(request);

            var uri = uriBuilder
                    .path("/usuarios/{username}/biblioteca")
                    .buildAndExpand(request.nombreUsuario(), libro.getId())
                    .toUri();

            var dtoRespuesta = new LibroAgregadoResponseDTO(
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getEstadoLectura(),
                    request.nombreUsuario()
            );

            return ResponseEntity.created(uri).body(dtoRespuesta);

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}