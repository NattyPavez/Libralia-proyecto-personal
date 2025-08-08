package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.domain.model.EstadoLectura;
import com.nataliapavez.libralia.domain.model.LibroPersonal;
import com.nataliapavez.libralia.domain.model.usuario.Usuario;
import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import com.nataliapavez.libralia.dto.request.EditarResenaPorTituloLibroLeidoDTO;
import com.nataliapavez.libralia.dto.response.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void actualizarResenaPorTitulo(String nombreUsuario, EditarResenaPorTituloLibroLeidoDTO datos) {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LibroPersonal libro = usuario.getLibros().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(datos.titulo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Libro con título '" + datos.titulo() + "' no encontrado en la biblioteca del usuario"));

        System.out.println("ID del libro encontrado: " + libro.getId());

        if (libro.getEstadoLectura() != EstadoLectura.LEIDO) {
            throw new IllegalStateException("Solo puedes editar reseñas de libros marcados como 'LEIDO'");
        }

        libro.editarResenia(datos.resenaPersonal());
        libro.calificar(datos.calificacionPersonal());
    }

    public UsuarioDTO obtenerUsuarioPublico(String username) {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UsuarioDTO(usuario);
    }

    public BibliotecaUsuarioDTO obtenerBibliotecaPublica(String username) {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new BibliotecaUsuarioDTO(usuario);
    }

    @Transactional
    public void eliminarLibroPorTitulo(String username, String titulo) {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<LibroPersonal> librosDelUsuario = usuario.getLibros();

        Optional<LibroPersonal> libroAEliminar = librosDelUsuario.stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();

        if (libroAEliminar.isPresent()) {
            librosDelUsuario.remove(libroAEliminar.get());
        } else {
            throw new RuntimeException("Libro con ese título no encontrado en la biblioteca del usuario");
        }
    }






}