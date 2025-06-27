package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.PerfilDTO;
import com.nataliapavez.libralia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    //buscador de perfil (probar con 43)
    @GetMapping("/perfil/{id}")
    public PerfilDTO obtenerPerfil(@PathVariable Long id) {
        return usuarioService.obtenerPerfil(id);
    }
}
