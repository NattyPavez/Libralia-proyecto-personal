package com.nataliapavez.libralia.controller;

import com.nataliapavez.libralia.domain.model.usuario.Usuario;
import com.nataliapavez.libralia.dto.request.LoginRequestDTO;
import com.nataliapavez.libralia.dto.request.RegistroUsuarioDTO;
import com.nataliapavez.libralia.dto.response.UsuarioDTO;
import com.nataliapavez.libralia.infra.security.DatosTokenJWT;
import com.nataliapavez.libralia.infra.security.TokenService;
import com.nataliapavez.libralia.service.RegistroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RegistroUsuarioService registroUsuarioService;

    @Autowired
    private AuthenticationManager manager;

    // Endpoint para registrar usuario
    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO datos) {
        UsuarioDTO usuarioRegistrado = registroUsuarioService.registrarUsuario(datos);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO datos) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.password());
        var authentication = manager.authenticate(authenticationToken);

        var userDetails = (UserDetails) authentication.getPrincipal();
        var jwt = tokenService.generarToken(userDetails.getUsername());

        return ResponseEntity.ok(new DatosTokenJWT(jwt));
    }

}
