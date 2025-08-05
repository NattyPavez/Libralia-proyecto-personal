package com.nataliapavez.libralia.domain.repository;

import com.nataliapavez.libralia.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.libros WHERE u.id = :id")
    Optional<Usuario> findByIdConLibros(@Param("id") Long id);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
