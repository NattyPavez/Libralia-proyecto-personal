package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.*;
import com.nataliapavez.libralia.repository.UsuarioRepository;
import com.nataliapavez.libralia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }
    @GetMapping("/usuarios")
    public List<UsuarioResumenDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioResumenDTO(u.getId(), u.getNombreUsuario(), u.getCorreo()))
                .toList();
    }
    @GetMapping("/perfil/{id}")
    public PerfilDTO obtenerPerfil(@PathVariable Long id) {
        return usuarioService.obtenerPerfil(id);
    }


    //insomnia probar con lana_talia_es
    @GetMapping("/usuarios/{username}/biblioteca/leidos")
    public List<LibroDTO> obtenerLibrosLeidos(@PathVariable String username) {
        return usuarioService.obtenerLibrosLeidosDeUsuario(username);
    }

    @GetMapping("/usuarios/{username}/biblioteca/por-leer")
    public List<LibroDTO> obtenerLibrosPorLeer(@PathVariable String username) {
        return usuarioService.obtenerLibrosPorLeerDeUsuario(username);
    }

    @GetMapping("/usuarios/{username}/biblioteca/leyendo")
    public List<LibroDTO> obtenerLibrosAbiertos(@PathVariable String username) {
        return usuarioService.obtenerLibrosAbiertosDeUsuario(username);
    }

    @GetMapping("/usuarios/{username}/biblioteca/leidos/resenas")
    public List<ResenaYCalificacionDeLibroPorUsuarioDTO> obtenerResenasPorUsuario(@PathVariable String username) {
        return usuarioService.obtenerResenasDeLibrosLeidosPorUsuario(username);
    }
    @PutMapping("/usuarios/{username}/biblioteca/leidos/editar-resena")
    public ResponseEntity<Void> editarResenaPorTitulo(
            @PathVariable String username,
            @RequestBody @Valid EditarResenaPorTituloLibroLeidoDTO datos) {
        usuarioService.actualizarResenaPorTitulo(username, datos);
        return ResponseEntity.noContent().build();
    }

}
