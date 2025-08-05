package com.nataliapavez.libralia.domain.repository;

import com.nataliapavez.libralia.domain.model.LibroPersonal;
import com.nataliapavez.libralia.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroPersonalRepository extends JpaRepository<LibroPersonal,Long> {

    Optional<LibroPersonal> findByTituloContainingIgnoreCase(String tituloLibro);

    Optional<LibroPersonal> findByTituloAndUsuario(String titulo, Usuario usuario);

    List<LibroPersonal> findTop5ByCalificacionPersonalIsNotNullOrderByCalificacionPersonalDesc();

    List<LibroPersonal> findByGeneroContainingIgnoreCase(String genero);

}
