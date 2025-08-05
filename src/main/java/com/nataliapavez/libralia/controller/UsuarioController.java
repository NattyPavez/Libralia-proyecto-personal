package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import com.nataliapavez.libralia.dto.request.EditarResenaPorTituloLibroLeidoDTO;
import com.nataliapavez.libralia.dto.request.EliminarLibroDTO;
import com.nataliapavez.libralia.dto.response.*;
import com.nataliapavez.libralia.service.UsuarioService;
import jakarta.validation.Valid;
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

    @GetMapping("/perfil/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfil(@PathVariable Long id) {
        var perfil = usuarioService.obtenerPerfil(id);
        return ResponseEntity.ok(perfil);
    }


    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResumenDTO>> listarUsuarios() {
        var usuarios =usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioResumenDTO(u.getId(), u.getNombreUsuario(), u.getCorreo()))
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{username}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPublico(@PathVariable String username) {
        var usuario = usuarioService.obtenerUsuarioPublico(username);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios/{username}/biblioteca")
    public ResponseEntity<BibliotecaUsuarioDTO> obtenerBibliotecaPublica(@PathVariable String username) {
        var biblioteca = usuarioService.obtenerBibliotecaPublica(username);
        return ResponseEntity.ok(biblioteca);
    }

    //insomnia probar con lana_talia_es
    @GetMapping("/usuarios/{username}/biblioteca/por-leer")
    public ResponseEntity<List<LibroDTO>> obtenerLibrosPorLeer(@PathVariable String username) {
        var libros = usuarioService.obtenerLibrosPorLeerDeUsuario(username);
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/usuarios/{username}/biblioteca/leyendo")
    public ResponseEntity<List<LibroDTO>> obtenerLibrosAbiertos(@PathVariable String username) {
        var libros = usuarioService.obtenerLibrosAbiertosDeUsuario(username);
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/usuarios/{username}/biblioteca/leidos")
    public ResponseEntity<List<LibroDTO>> obtenerLibrosLeidos(@PathVariable String username) {
        var libros =  usuarioService.obtenerLibrosLeidosDeUsuario(username);
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/usuarios/{username}/biblioteca/leidos/resenas")
    public ResponseEntity<List<ResenaYCalificacionDeLibroPorUsuarioDTO>> obtenerResenasPorUsuario(@PathVariable String username) {
        var libros = usuarioService.obtenerResenasDeLibrosLeidosPorUsuario(username);
        return ResponseEntity.ok(libros);
    }

    @PutMapping("/usuarios/{username}/biblioteca/leidos/editar-resena")
    public ResponseEntity<Void> editarResenaPorTitulo(
            @PathVariable String username,
            @RequestBody @Valid EditarResenaPorTituloLibroLeidoDTO datos) {
        usuarioService.actualizarResenaPorTitulo(username, datos);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("usuarios/{username}/biblioteca/eliminar-libro")
    public ResponseEntity<String> eliminarLibro(
            @PathVariable String username,
            @RequestBody EliminarLibroDTO dto) {

        usuarioService.eliminarLibroPorTitulo(username, dto.titulo());
        return ResponseEntity.ok("Libro eliminado exitosamente");
    }


}
