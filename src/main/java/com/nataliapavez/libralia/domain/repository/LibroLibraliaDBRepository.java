package com.nataliapavez.libralia.repository;

import com.nataliapavez.libralia.model.LibroLibraliaDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroLibraliaDBRepository extends JpaRepository<LibroLibraliaDB, Long> {

    Optional<LibroLibraliaDB> findByTituloIgnoreCaseAndAutorIgnoreCase(String titulo, String Autor);
}
