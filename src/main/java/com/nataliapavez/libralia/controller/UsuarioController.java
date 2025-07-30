package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.dto.PerfilDTO;
import com.nataliapavez.libralia.dto.ResenaYCalificacionDeLibroPorUsuarioDTO;
import com.nataliapavez.libralia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    //buscador de perfil (probar con 43)
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

}
