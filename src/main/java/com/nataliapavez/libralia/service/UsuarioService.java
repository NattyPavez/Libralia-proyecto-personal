package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.dto.PerfilDTO;
import com.nataliapavez.libralia.dto.ResenaYCalificacionDeLibroPorUsuarioDTO;
import com.nataliapavez.libralia.dto.UsuarioDTO;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.model.Usuario;
import com.nataliapavez.libralia.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    public PerfilDTO obtenerPerfil(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

        List<LibroDTO> librosLeidos = usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.LEIDO)
                .map(LibroDTO::new)
                .toList();

        List<LibroDTO> librosPorLeer = usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.POR_LEER)
                .map(LibroDTO::new)
                .toList();

        List<LibroDTO> librosLeyendo = usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.LEYENDO)
                .map(LibroDTO::new)
                .toList();

        return new PerfilDTO(usuarioDTO, librosLeidos, librosPorLeer, librosLeyendo);
    }

    private Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public List<LibroDTO> obtenerLibrosLeidosDeUsuario(String username) {
        Usuario usuario = obtenerUsuarioPorUsername(username);

        return usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.LEIDO)
                .map(LibroDTO::new)
                .toList();
    }

    public List<LibroDTO> obtenerLibrosPorLeerDeUsuario(String username) {
        Usuario usuario = obtenerUsuarioPorUsername(username);

        return usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.POR_LEER)
                .map(LibroDTO::new)
                .toList();
    }


    public List<LibroDTO> obtenerLibrosAbiertosDeUsuario(String username) {
        Usuario usuario = obtenerUsuarioPorUsername(username);

        return usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.LEYENDO)
                .map(LibroDTO::new)
                .toList();
    }

    public List<ResenaYCalificacionDeLibroPorUsuarioDTO> obtenerResenasDeLibrosLeidosPorUsuario(String username) {
        Usuario usuario = obtenerUsuarioPorUsername(username);

        return usuario.getLibros().stream()
                .filter(libro -> libro.getEstadoLectura() == EstadoLectura.LEIDO)
                .filter(libro -> libro.getReseniaPersonal() != null && !libro.getReseniaPersonal().isBlank())
                .map(ResenaYCalificacionDeLibroPorUsuarioDTO::new)
                .toList();
    }

}