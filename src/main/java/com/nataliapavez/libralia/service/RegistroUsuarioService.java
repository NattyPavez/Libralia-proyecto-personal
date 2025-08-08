package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.domain.model.usuario.Rol;
import com.nataliapavez.libralia.domain.model.usuario.Usuario;
import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import com.nataliapavez.libralia.dto.request.RegistroUsuarioDTO;
import com.nataliapavez.libralia.dto.response.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroUsuarioService {

    private final UsuarioRepository usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public RegistroUsuarioService(UsuarioRepository usuarioRepositorio,
                                  PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional
    public UsuarioDTO registrarUsuario(RegistroUsuarioDTO datos) {
        String login = datos.login().trim().toLowerCase();
        String correo = datos.correo().trim().toLowerCase();

        // Validar login único
        if (usuarioRepositorio.existsByLogin(login)) {
            throw new IllegalArgumentException("El login ya está en uso");
        }
        if (usuarioRepositorio.existsByCorreo(correo)) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        // Codificar la contraseña
        String passwordEncriptado = passwordEncoder.encode(datos.password());

        // Crear usuario con valores iniciales
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(datos.nombreUsuario());
        nuevoUsuario.setLogin(login);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setPassword(passwordEncriptado);
        nuevoUsuario.setRol(Rol.USER);
        nuevoUsuario.setActivo(true);


        if (datos.edad() != null) nuevoUsuario.setEdad(datos.edad());
        nuevoUsuario.setDescripcion(
                datos.descripcion() != null && !datos.descripcion().isBlank() ? datos.descripcion().trim() : null
        );
        nuevoUsuario.setEnlaces(
                datos.enlaces() != null && !datos.enlaces().isBlank() ? datos.enlaces().trim() : null
        );

        // Guardar usuario en la base de datos
        try {
            Usuario usuarioGuardado = usuarioRepositorio.save(nuevoUsuario);
            return new UsuarioDTO(usuarioGuardado);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // por si la BD tiene los UNIQUE y hubo carrera
            throw new IllegalArgumentException("Login o correo ya registrados", e);
        }

    }
}
