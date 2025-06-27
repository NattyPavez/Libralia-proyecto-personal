package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.LibroLibraliaDTO;
import com.nataliapavez.libralia.model.LibroLibraliaDB;
import com.nataliapavez.libralia.repository.LibroLibraliaDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<LibroLibraliaDTO> obtenerTodosLosLibrosDTO() {
        return libroLibraliaRepo.findAll()
                .stream()
                .map(LibroLibraliaDTO::desdeEntidad)
                .toList();
    }

}
