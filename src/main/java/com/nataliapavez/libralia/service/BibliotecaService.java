package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.AgregarLibroRequestDTO;
import com.nataliapavez.libralia.dto.LibroGoogleDTO;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.model.LibroLibraliaDB;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.model.Usuario;
import com.nataliapavez.libralia.repository.LibroLibraliaDBRepository;
import com.nataliapavez.libralia.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BibliotecaService {

    private final LibroLibraliaDBRepository libroLibraliaDBRepository;
    private final UsuarioRepository usuarioRepository;

    public BibliotecaService(LibroLibraliaDBRepository libroLibraliaDBRepository, UsuarioRepository usuarioRepository) {
        this.libroLibraliaDBRepository = libroLibraliaDBRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void agregarLibroDesdeDTO(AgregarLibroRequestDTO request) {
        try {
            LibroGoogleDTO libroDTO = request.libro();
            System.out.println("CALIFICACION GOOGLE: " + libroDTO.calificacionGoogle());

            EstadoLectura estado = EstadoLectura.valueOf(request.estadoLectura().toUpperCase());

            // Paso 1: Buscar o crear libro base (libroLibraliaDB)
            Optional<LibroLibraliaDB> existente = libroLibraliaDBRepository.findByTituloIgnoreCaseAndAutorIgnoreCase(
                    libroDTO.titulo(), libroDTO.autor()
            );

            LibroLibraliaDB libroBase = existente.orElseGet(() -> {
                LibroLibraliaDB nuevo = new LibroLibraliaDB(
                        libroDTO.titulo(),
                        libroDTO.autor(),
                        libroDTO.descripcion(),
                        libroDTO.urlPortada(),
                        libroDTO.genero(),
                        libroDTO.anioPublicacion(),
                        libroDTO.calificacionGoogle()
                );
                return libroLibraliaDBRepository.save(nuevo);
            });

            // Paso 2: Buscar al usuario que vamos a usar
            Usuario usuario = usuarioRepository.findByNombreUsuario(request.nombreUsuario())
                    .orElseThrow(() -> new RuntimeException("No se encontró el usuario: " + request.nombreUsuario()));

            // Verificar si el usuario ya tiene este libro
            boolean libroYaExiste = usuario.getLibros().stream()
                    .anyMatch(libro -> libro.getTitulo().equalsIgnoreCase(libroDTO.titulo())
                            && libro.getAutor().equalsIgnoreCase(libroDTO.autor()));

            if (libroYaExiste) {
                throw new RuntimeException("El usuario ya tiene este libro en su biblioteca");
            }

            // Paso 3: Crear el libro personalizado con estado
            LibroPersonal libroPersonal = new LibroPersonal(libroBase, estado);
            usuario.agregarLibro(libroPersonal);

            // Paso 4: Guardar el usuario para persistir el libro
            usuarioRepository.save(usuario);

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de lectura no válido. Los valores válidos son: LEIDO, LEYENDO, POR_LEER", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el libro: " + e.getMessage(), e);
        }
    }
}