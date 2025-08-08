package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.dto.request.LoginRequestDTO;
import com.nataliapavez.libralia.dto.request.RegistroUsuarioDTO;
import com.nataliapavez.libralia.dto.response.UsuarioDTO;
import com.nataliapavez.libralia.service.RegistroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegistroUsuarioService registroUsuarioService;

    @Autowired
    private AuthenticationManager manager;

    // Endpoint para registrar usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO datos) {
        UsuarioDTO usuarioRegistrado = registroUsuarioService.registrarUsuario(datos);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO datos) {
        var token = new UsernamePasswordAuthenticationToken(datos.login(), datos.password());
        var authentication = manager.authenticate(token);  // lanza excepción si no coincide
        return ResponseEntity.ok().build();        // por ahora 200 OK (luego devolveremos JWT)
    }

}
