package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.response.LibroLibraliaDTO;
import com.nataliapavez.libralia.domain.model.LibroLibraliaDB;
import com.nataliapavez.libralia.domain.repository.LibroLibraliaDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LibroLibraliaDBService {

    @Autowired
    private LibroLibraliaDBRepository libroLibraliaRepo;

    public LibroLibraliaDB obtenerLibroNuevo(String titulo, String autor, String genero, String descripcion,
                                             String urlPortada, Integer anioDePublicacion, Double calificacionGoogle) {
        return libroLibraliaRepo
                .findByTituloIgnoreCaseAndAutorIgnoreCase(titulo, autor)
                .orElseGet(() -> {
                    LibroLibraliaDB nuevoLibro = new LibroLibraliaDB(titulo, autor, genero, descripcion, urlPortada, anioDePublicacion, calificacionGoogle);
                    return libroLibraliaRepo.save(nuevoLibro);
                });
    }

    public Page<LibroLibraliaDTO> obtenerTodosLosLibrosDTO(Pageable pageable) {
        return libroLibraliaRepo.findAll(pageable)
                .map(LibroLibraliaDTO::desdeEntidad);
    }

}
