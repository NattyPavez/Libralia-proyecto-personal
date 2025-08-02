package com.nataliapavez.libralia.service;

import com.nataliapavez.libralia.dto.EditarResenaPorTituloLibroLeidoDTO;
import com.nataliapavez.libralia.dto.LibroDTO;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.model.Usuario;
import com.nataliapavez.libralia.repository.LibroPersonalRepository;
import com.nataliapavez.libralia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class LibroPersonalService {

    @Autowired
    private LibroPersonalRepository libroPersonalRepository;


    public List<LibroDTO> obtenerLibros() {
        return libroPersonalRepository.findAll()
                .stream()
                .map(LibroDTO::new)
                .toList();
    }

    @GetMapping("/")
    public String inicio() {
        return "¡Bienvenido/a a Libralia API";
    }

}
