package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.request.AgregarLibroRequestDTO;
import com.nataliapavez.libralia.dto.external.LibroGoogleDTO;
import com.nataliapavez.libralia.domain.model.EstadoLectura;
import com.nataliapavez.libralia.domain.model.LibroLibraliaDB;
import com.nataliapavez.libralia.domain.model.LibroPersonal;
import com.nataliapavez.libralia.domain.model.usuario.Usuario;
import com.nataliapavez.libralia.domain.repository.LibroLibraliaDBRepository;
import com.nataliapavez.libralia.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BibliotecaService {

    private final LibroLibraliaDBRepository libroLibraliaDBRepository;
    private final UsuarioRepository usuarioRepository;

    public BibliotecaService(LibroLibraliaDBRepository libroLibraliaDBRepository, UsuarioRepository usuarioRepository) {
        this.libroLibraliaDBRepository = libroLibraliaDBRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public LibroPersonal agregarLibroDesdeDTO(AgregarLibroRequestDTO request) {
        try {
            LibroGoogleDTO libroDTO = request.libro();
            EstadoLectura estado = request.estadoLectura();


            // Paso 1: Buscar o crear libro base (libroLibraliaDB)
            LibroLibraliaDB libroBase = libroLibraliaDBRepository
                    .findByTituloIgnoreCaseAndAutorIgnoreCase(libroDTO.titulo(), libroDTO.autor())
                    .orElseGet(() -> {

                        var nuevo = new LibroLibraliaDB(
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

            // Paso 4: Guardar usuario (persistencia en cascada del libro)
            usuarioRepository.save(usuario);

            return libroPersonal;

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de lectura no válido. Los valores válidos son: LEIDO, LEYENDO, POR_LEER", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el libro: " + e.getMessage(), e);
        }
    }
}